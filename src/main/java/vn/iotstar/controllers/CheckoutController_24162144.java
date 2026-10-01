package vn.iotstar.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.model.Order_24162144;
import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IOrderService_24162144;
import vn.iotstar.service.impl.OrderServiceImpl_24162144;

@WebServlet(urlPatterns = {"/checkout"})
public class CheckoutController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IOrderService_24162144 orderService = new OrderServiceImpl_24162144();

    @SuppressWarnings("unchecked")
    private Map<Integer, CartItem_24162144> getCart(HttpSession session) {
        return (Map<Integer, CartItem_24162144>) session.getAttribute("cart");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User_24162144 account = (User_24162144) session.getAttribute("account");

        // 1. Bắt buộc đăng nhập
        if (account == null) {
            session.setAttribute("error", "Vui lòng đăng nhập để tiến hành thanh toán!");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Kiểm tra giỏ hàng
        Map<Integer, CartItem_24162144> cart = getCart(session);
        if (cart == null || cart.isEmpty()) {
            session.setAttribute("error", "Giỏ hàng của bạn đang trống! Hãy chọn sách trước khi thanh toán.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        double totalAmount = 0;
        int totalItems = 0;
        for (CartItem_24162144 item : cart.values()) {
            totalAmount += item.getTotalPrice();
            totalItems += item.getQuantity();
        }

        req.setAttribute("cartItems", cart.values());
        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("user", account);

        req.getRequestDispatcher("/WEB-INF/views/web/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User_24162144 account = (User_24162144) session.getAttribute("account");

        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Map<Integer, CartItem_24162144> cart = getCart(session);
        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String note = req.getParameter("note");

        if (fullname == null || fullname.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ Họ tên, Số điện thoại và Địa chỉ nhận hàng!");
            doGet(req, resp);
            return;
        }

        double totalAmount = 0;
        for (CartItem_24162144 item : cart.values()) {
            totalAmount += item.getTotalPrice();
        }

        Order_24162144 order = new Order_24162144();
        order.setUserId(account.getId());
        order.setFullname(fullname.trim());
        order.setPhone(phone.trim());
        order.setAddress(address.trim());
        order.setNote(note != null ? note.trim() : "");
        order.setTotalAmount(totalAmount);
        order.setPaymentMethod("COD");
        order.setStatus("Đơn hàng mới");

        int orderId = orderService.createOrder(order, new ArrayList<>(cart.values()));
        if (orderId > 0) {
            cart.clear();
            session.removeAttribute("cart");
            session.setAttribute("orderSuccessMsg", "Đặt hàng thành công với phương thức COD! Mã đơn hàng #" + orderId);
            resp.sendRedirect(req.getContextPath() + "/orders?orderSuccess=true&orderId=" + orderId);
        } else {
            session.setAttribute("error", "Đặt hàng thất bại do số lượng tồn kho của một số cuốn sách trong giỏ không đủ. Vui lòng kiểm tra lại!");
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}
