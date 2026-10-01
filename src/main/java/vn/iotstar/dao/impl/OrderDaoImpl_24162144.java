package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.IBookDao_24162144;
import vn.iotstar.dao.IOrderDao_24162144;
import vn.iotstar.model.Book_24162144;
import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.model.OrderDetail_24162144;
import vn.iotstar.model.Order_24162144;

public class OrderDaoImpl_24162144 implements IOrderDao_24162144 {

    private IBookDao_24162144 bookDao = new BookDaoImpl_24162144();

    private Connection getConnection() throws Exception {
        return new DBConnection().getConnection();
    }

    @Override
    public int createOrder(Order_24162144 order, List<CartItem_24162144> items) {
        String sqlOrder = "INSERT INTO dbo.orders (user_id, fullname, phone, address, note, total_amount, payment_method, status, created_at) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
        String sqlDetail = "INSERT INTO dbo.order_details (order_id, bookid, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                int orderId = 0;
                try (PreparedStatement psOrder = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)) {
                    psOrder.setInt(1, order.getUserId());
                    psOrder.setString(2, order.getFullname());
                    psOrder.setString(3, order.getPhone());
                    psOrder.setString(4, order.getAddress());
                    psOrder.setString(5, order.getNote());
                    psOrder.setDouble(6, order.getTotalAmount());
                    psOrder.setString(7, order.getPaymentMethod());
                    psOrder.setString(8, order.getStatus());
                    psOrder.executeUpdate();

                    try (ResultSet rs = psOrder.getGeneratedKeys()) {
                        if (rs.next()) {
                            orderId = rs.getInt(1);
                        }
                    }
                }

                if (orderId == 0) {
                    throw new SQLException("Không thể lấy order_id sinh tự động.");
                }

                try (PreparedStatement psDetail = conn.prepareStatement(sqlDetail)) {
                    for (CartItem_24162144 item : items) {
                        psDetail.setInt(1, orderId);
                        psDetail.setInt(2, item.getBook().getBookid());
                        psDetail.setDouble(3, item.getBook().getPrice());
                        psDetail.setInt(4, item.getQuantity());
                        psDetail.executeUpdate();

                        // Trừ tồn kho sách trong cùng transaction
                        boolean stockOk = bookDao.deductStock(conn, item.getBook().getBookid(), item.getQuantity());
                        if (!stockOk) {
                            throw new SQLException("Hết hàng hoặc số lượng tồn không đủ cho sách ID: " + item.getBook().getBookid());
                        }
                    }
                }

                conn.commit();
                return orderId;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return 0;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Order_24162144> findOrdersByUserId(int userId, String status) {
        List<Order_24162144> list = new ArrayList<>();
        boolean filterByStatus = (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status.trim()));
        String sql = filterByStatus 
            ? "SELECT * FROM dbo.orders WHERE user_id = ? AND status = ? ORDER BY created_at DESC"
            : "SELECT * FROM dbo.orders WHERE user_id = ? ORDER BY created_at DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            if (filterByStatus) {
                ps.setString(2, status.trim());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOrder(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order_24162144 findOrderById(int orderId) {
        String sql = "SELECT * FROM dbo.orders WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Order_24162144 order = mapResultSetToOrder(rs);
                    order.setItems(findDetailsByOrderId(orderId));
                    return order;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrderDetail_24162144> findDetailsByOrderId(int orderId) {
        List<OrderDetail_24162144> list = new ArrayList<>();
        String sql = "SELECT od.*, b.title, b.isbn, b.cover_image, b.publisher, b.price as current_book_price "
                   + "FROM dbo.order_details od "
                   + "JOIN dbo.books b ON od.bookid = b.bookid "
                   + "WHERE od.order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderDetail_24162144 detail = new OrderDetail_24162144();
                    detail.setId(rs.getInt("id"));
                    detail.setOrderId(rs.getInt("order_id"));
                    detail.setBookId(rs.getInt("bookid"));
                    detail.setPrice(rs.getDouble("price"));
                    detail.setQuantity(rs.getInt("quantity"));

                    Book_24162144 book = new Book_24162144();
                    book.setBookid(rs.getInt("bookid"));
                    book.setTitle(rs.getString("title"));
                    book.setIsbn(rs.getInt("isbn"));
                    book.setCover_image(rs.getString("cover_image"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPrice(rs.getDouble("current_book_price"));

                    detail.setBook(book);
                    list.add(detail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean cancelOrder(int orderId, int userId) {
        String sqlCheck = "SELECT status FROM dbo.orders WHERE order_id = ? AND user_id = ?";
        String sqlUpdate = "UPDATE dbo.orders SET status = N'Đơn hàng hủy' WHERE order_id = ? AND user_id = ?";
        String sqlGetItems = "SELECT bookid, quantity FROM dbo.order_details WHERE order_id = ?";

        try (Connection conn = getConnection()) {
            // Kiểm tra trạng thái hiện tại của đơn hàng
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, orderId);
                psCheck.setInt(2, userId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    String currentStatus = rs.getString("status");
                    // Chỉ cho phép hủy khi đơn ở trạng thái "Đơn hàng mới" hoặc "Đã xác nhận"
                    if (!"Đơn hàng mới".equalsIgnoreCase(currentStatus) && !"Đã xác nhận".equalsIgnoreCase(currentStatus)) {
                        return false;
                    }
                }
            }

            conn.setAutoCommit(false);
            try {
                // Cập nhật trạng thái sang "Đơn hàng hủy"
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, orderId);
                    psUpdate.setInt(2, userId);
                    psUpdate.executeUpdate();
                }

                // Lấy danh sách sách và số lượng để hoàn trả tồn kho
                try (PreparedStatement psItems = conn.prepareStatement(sqlGetItems)) {
                    psItems.setInt(1, orderId);
                    try (ResultSet rsItems = psItems.executeQuery()) {
                        while (rsItems.next()) {
                            int bookId = rsItems.getInt("bookid");
                            int quantity = rsItems.getInt("quantity");
                            bookDao.restoreStock(conn, bookId, quantity);
                        }
                    }
                }

                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean returnOrder(int orderId, int userId) {
        String sqlCheck = "SELECT status FROM dbo.orders WHERE order_id = ? AND user_id = ?";
        String sqlUpdate = "UPDATE dbo.orders SET status = N'Đơn hàng hoàn' WHERE order_id = ? AND user_id = ?";
        String sqlGetItems = "SELECT bookid, quantity FROM dbo.order_details WHERE order_id = ?";

        try (Connection conn = getConnection()) {
            // Kiểm tra trạng thái hiện tại
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, orderId);
                psCheck.setInt(2, userId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        return false;
                    }
                    String currentStatus = rs.getString("status");
                    // Chỉ cho phép trả hàng khi đơn ở trạng thái "Đã giao"
                    if (!"Đã giao".equalsIgnoreCase(currentStatus)) {
                        return false;
                    }
                }
            }

            conn.setAutoCommit(false);
            try {
                // Cập nhật trạng thái sang "Đơn hàng hoàn"
                try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                    psUpdate.setInt(1, orderId);
                    psUpdate.setInt(2, userId);
                    psUpdate.executeUpdate();
                }

                // Hoàn lại số lượng tồn kho sách khi trả hàng
                try (PreparedStatement psItems = conn.prepareStatement(sqlGetItems)) {
                    psItems.setInt(1, orderId);
                    try (ResultSet rsItems = psItems.executeQuery()) {
                        while (rsItems.next()) {
                            int bookId = rsItems.getInt("bookid");
                            int quantity = rsItems.getInt("quantity");
                            bookDao.restoreStock(conn, bookId, quantity);
                        }
                    }
                }

                conn.commit();
                return true;
            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                return false;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Order_24162144 mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order_24162144 order = new Order_24162144();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setFullname(rs.getString("fullname"));
        order.setPhone(rs.getString("phone"));
        order.setAddress(rs.getString("address"));
        order.setNote(rs.getString("note"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setStatus(rs.getString("status"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        return order;
    }
}
