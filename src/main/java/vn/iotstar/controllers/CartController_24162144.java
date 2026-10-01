package vn.iotstar.controllers;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.Book_24162144;
import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.service.IBookService_24162144;
import vn.iotstar.service.impl.BookServiceImpl_24162144;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/delete", "/cart/clear"})
public class CartController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IBookService_24162144 bookService = new BookServiceImpl_24162144();

    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem_24162144> getCart(HttpSession session) {
        Map<Integer, CartItem_24162144> cart = (Map<Integer, CartItem_24162144>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        Map<Integer, CartItem_24162144> cart = getCart(session);

        if ("/cart/add".equals(path)) {
            handleAdd(req, resp, session, cart);
        } else if ("/cart/delete".equals(path)) {
            handleDelete(req, resp, session, cart);
        } else if ("/cart/clear".equals(path)) {
            handleClear(req, resp, session, cart);
        } else {
            // Hiển thị trang giỏ hàng (/cart)
            double totalAmount = 0;
            int totalItems = 0;
            for (CartItem_24162144 item : cart.values()) {
                totalAmount += item.getTotalPrice();
                totalItems += item.getQuantity();
            }
            req.setAttribute("cartItems", cart.values());
            req.setAttribute("totalAmount", totalAmount);
            req.setAttribute("totalItems", totalItems);
            req.getRequestDispatcher("/WEB-INF/views/web/cart.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        Map<Integer, CartItem_24162144> cart = getCart(session);

        if ("/cart/add".equals(path)) {
            handleAdd(req, resp, session, cart);
        } else if ("/cart/update".equals(path)) {
            handleUpdate(req, resp, session, cart);
        } else if ("/cart/delete".equals(path)) {
            handleDelete(req, resp, session, cart);
        } else if ("/cart/clear".equals(path)) {
            handleClear(req, resp, session, cart);
        } else {
            doGet(req, resp);
        }
    }

    private void handleAdd(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
            Map<Integer, CartItem_24162144> cart) throws IOException {
        int bookId = 0;
        int quantity = 1;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
        } catch (Exception e) {
            try {
                bookId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}
        }

        try {
            String qtyParam = req.getParameter("quantity");
            if (qtyParam != null && !qtyParam.trim().isEmpty()) {
                quantity = Integer.parseInt(qtyParam.trim());
                if (quantity <= 0) quantity = 1;
            }
        } catch (Exception ignored) {}

        Book_24162144 book = bookService.findById(bookId);
        if (book != null) {
            int currentQty = cart.containsKey(bookId) ? cart.get(bookId).getQuantity() : 0;
            if ((currentQty + quantity) > book.getQuantity()) {
                session.setAttribute("error", "Số lượng yêu cầu vượt quá số lượng sách còn trong kho (" + book.getQuantity() + ")!");
            } else {
                if (cart.containsKey(bookId)) {
                    cart.get(bookId).setQuantity(currentQty + quantity);
                } else {
                    cart.put(bookId, new CartItem_24162144(book, quantity));
                }
                session.setAttribute("message", "Đã thêm cuốn \"" + book.getTitle() + "\" vào giỏ hàng thành công!");
            }
        } else {
            session.setAttribute("error", "Không tìm thấy thông tin cuốn sách yêu cầu!");
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
            Map<Integer, CartItem_24162144> cart) throws IOException {
        int bookId = 0;
        int quantity = 0;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (Exception ignored) {}

        if (bookId > 0 && cart.containsKey(bookId)) {
            if (quantity <= 0) {
                cart.remove(bookId);
                session.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
            } else {
                Book_24162144 book = bookService.findById(bookId);
                if (book != null && quantity > book.getQuantity()) {
                    cart.get(bookId).setQuantity(book.getQuantity());
                    session.setAttribute("error", "Số lượng sách \"" + book.getTitle() + "\" trong kho chỉ còn " + book.getQuantity() + " cuốn!");
                } else {
                    cart.get(bookId).setQuantity(quantity);
                    session.setAttribute("message", "Đã cập nhật số lượng thành công!");
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
            Map<Integer, CartItem_24162144> cart) throws IOException {
        int bookId = 0;
        try {
            bookId = Integer.parseInt(req.getParameter("bookId"));
        } catch (Exception e) {
            try {
                bookId = Integer.parseInt(req.getParameter("id"));
            } catch (Exception ignored) {}
        }

        if (bookId > 0 && cart.containsKey(bookId)) {
            String title = cart.get(bookId).getBook().getTitle();
            cart.remove(bookId);
            session.setAttribute("message", "Đã xóa \"" + title + "\" khỏi giỏ hàng!");
        }

        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    private void handleClear(HttpServletRequest req, HttpServletResponse resp, HttpSession session,
            Map<Integer, CartItem_24162144> cart) throws IOException {
        cart.clear();
        session.removeAttribute("cart");
        session.setAttribute("message", "Đã làm trống giỏ hàng!");
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
