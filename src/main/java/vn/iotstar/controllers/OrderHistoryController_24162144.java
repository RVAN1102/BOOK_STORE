package vn.iotstar.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.Book_24162144;
import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.model.OrderDetail_24162144;
import vn.iotstar.model.Order_24162144;
import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IBookService_24162144;
import vn.iotstar.service.IOrderService_24162144;
import vn.iotstar.service.impl.BookServiceImpl_24162144;
import vn.iotstar.service.impl.OrderServiceImpl_24162144;

@WebServlet(urlPatterns = {"/orders", "/orders/detail", "/orders/cancel", "/orders/return", "/orders/reorder"})
public class OrderHistoryController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IOrderService_24162144 orderService = new OrderServiceImpl_24162144();
    private IBookService_24162144 bookService = new BookServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User_24162144 account = (User_24162144) session.getAttribute("account");

        if (account == null) {
            session.setAttribute("error", "Vui lòng đăng nhập để xem lịch sử đơn hàng!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        String action = req.getParameter("action");

        if ("/orders/cancel".equals(path) || ("cancel".equalsIgnoreCase(action) && "/orders".equals(path))) {
            int orderId = 0;
            try {
                orderId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}

            boolean success = orderService.cancelOrder(orderId, account.getId());
            if (success) {
                session.setAttribute("message", "Đã hủy đơn hàng #" + orderId + " thành công và hoàn trả số lượng vào kho!");
            } else {
                session.setAttribute("error", "Không thể hủy đơn hàng #" + orderId + "! Đơn hàng chỉ có thể hủy khi ở trạng thái 'Đơn hàng mới' hoặc 'Đã xác nhận'.");
            }
            resp.sendRedirect(req.getContextPath() + "/orders?status=" + URLEncoder.encode("Đơn hàng hủy", StandardCharsets.UTF_8));
            return;
        } else if ("/orders/return".equals(path) || ("return".equalsIgnoreCase(action) && "/orders".equals(path))) {
            int orderId = 0;
            try {
                orderId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}

            boolean success = orderService.returnOrder(orderId, account.getId());
            if (success) {
                session.setAttribute("message", "Đã gửi yêu cầu trả hàng cho đơn hàng #" + orderId + " thành công!");
            } else {
                session.setAttribute("error", "Không thể trả hàng cho đơn hàng #" + orderId + "! Chỉ có thể yêu cầu trả hàng khi đơn ở trạng thái 'Đã giao'.");
            }
            resp.sendRedirect(req.getContextPath() + "/orders?status=" + URLEncoder.encode("Đơn hàng hoàn", StandardCharsets.UTF_8));
            return;
        } else if ("/orders/reorder".equals(path) || ("reorder".equalsIgnoreCase(action) && "/orders".equals(path))) {
            int orderId = 0;
            try {
                orderId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}

            Order_24162144 order = orderService.findOrderById(orderId);
            if (order == null || (order.getUserId() != account.getId() && !account.is_admin())) {
                session.setAttribute("error", "Không tìm thấy đơn hàng hoặc bạn không có quyền thao tác!");
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }

            List<OrderDetail_24162144> details = orderService.findDetailsByOrderId(orderId);
            if (details == null || details.isEmpty()) {
                session.setAttribute("error", "Đơn hàng này không có sản phẩm nào để mua lại!");
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }

            @SuppressWarnings("unchecked")
            Map<Integer, CartItem_24162144> cart = (Map<Integer, CartItem_24162144>) session.getAttribute("cart");
            if (cart == null) {
                cart = new LinkedHashMap<>();
                session.setAttribute("cart", cart);
            }

            int addedCount = 0;
            StringBuilder warningMsg = new StringBuilder();

            for (OrderDetail_24162144 detail : details) {
                Book_24162144 book = bookService.findById(detail.getBookId());
                if (book == null) {
                    warningMsg.append("Sách ID #").append(detail.getBookId()).append(" không còn tồn tại. ");
                    continue;
                }
                int curQtyInCart = cart.containsKey(book.getBookid()) ? cart.get(book.getBookid()).getQuantity() : 0;
                int requestedQty = detail.getQuantity();

                if (book.getQuantity() <= 0) {
                    warningMsg.append("Cuốn \"").append(book.getTitle()).append("\" hiện đã hết hàng trong kho. ");
                } else if ((curQtyInCart + requestedQty) > book.getQuantity()) {
                    int canAdd = book.getQuantity() - curQtyInCart;
                    if (canAdd > 0) {
                        cart.put(book.getBookid(), new CartItem_24162144(book, book.getQuantity()));
                        addedCount++;
                    }
                    warningMsg.append("Cuốn \"").append(book.getTitle()).append("\" chỉ có thể lấy tối đa ").append(book.getQuantity()).append(" cuốn do tồn kho có hạn. ");
                } else {
                    cart.put(book.getBookid(), new CartItem_24162144(book, curQtyInCart + requestedQty));
                    addedCount++;
                }
            }

            if (addedCount > 0) {
                if (warningMsg.length() > 0) {
                    session.setAttribute("message", "Đã thêm sản phẩm từ đơn #" + orderId + " vào giỏ hàng! Lưu ý: " + warningMsg.toString());
                } else {
                    session.setAttribute("message", "Đã thêm các sản phẩm từ đơn hàng #" + orderId + " vào giỏ hàng thành công!");
                }
            } else {
                session.setAttribute("error", "Không thể mua lại đơn hàng: " + warningMsg.toString());
            }

            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        } else if ("/orders/detail".equals(path)) {
            int orderId = 0;
            try {
                orderId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}

            Order_24162144 order = orderService.findOrderById(orderId);
            if (order == null || (order.getUserId() != account.getId() && !account.is_admin())) {
                session.setAttribute("error", "Không tìm thấy đơn hàng hoặc bạn không có quyền truy cập đơn này!");
                resp.sendRedirect(req.getContextPath() + "/orders");
                return;
            }

            req.setAttribute("order", order);
            req.getRequestDispatcher("/WEB-INF/views/web/order-detail.jsp").forward(req, resp);
        } else {
            // Danh sách đơn hàng (/orders)
            String status = req.getParameter("status");
            if (status == null || status.trim().isEmpty()) {
                status = "ALL";
            } else {
                status = status.trim();
            }

            List<Order_24162144> orders = orderService.findOrdersByUserId(account.getId(), status);
            req.setAttribute("orders", orders);
            req.setAttribute("currentStatus", status);
            req.getRequestDispatcher("/WEB-INF/views/web/orders.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
