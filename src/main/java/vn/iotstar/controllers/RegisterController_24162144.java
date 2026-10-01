package vn.iotstar.controllers;

import java.io.IOException;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IUserService_24162144;
import vn.iotstar.service.impl.UserServiceImpl_24162144;
import vn.iotstar.util.EmailUtil_24162144;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162144 userService = new UserServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");
        String phoneStr = req.getParameter("phone");
        String passwd = req.getParameter("passwd");

        if (fullname == null || fullname.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            passwd == null || passwd.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ các trường thông tin!");
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        email = email.trim();
        if (userService.checkExistEmail(email)) {
            req.setAttribute("error", "Email này đã được sử dụng. Vui lòng chọn email khác!");
            req.setAttribute("fullname", fullname);
            req.setAttribute("email", email);
            req.setAttribute("phone", phoneStr);
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        int phone = 0;
        if (phoneStr != null && !phoneStr.trim().isEmpty()) {
            try {
                // Loại bỏ ký tự không phải số nếu có
                String cleanPhone = phoneStr.replaceAll("[^0-9]", "");
                if (!cleanPhone.isEmpty()) {
                    phone = Integer.parseInt(cleanPhone);
                }
            } catch (NumberFormatException e) {
                phone = 0;
            }
        }

        // Tạo mã OTP ngẫu nhiên 6 chữ số
        String otp = String.format("%06d", new Random().nextInt(1000000));

        User_24162144 tempUser = new User_24162144();
        tempUser.setFullname(fullname.trim());
        tempUser.setEmail(email);
        tempUser.setPhone(phone);
        tempUser.setPasswd(passwd.trim());
        tempUser.setIs_admin(false);

        // Lưu thông tin tạm và OTP vào Session
        HttpSession session = req.getSession(true);
        session.setAttribute("tempUser", tempUser);
        session.setAttribute("otp", otp);

        // Gửi OTP qua email (kèm print ra console)
        EmailUtil_24162144.sendOtp(email, otp);

        resp.sendRedirect(req.getContextPath() + "/verify-otp");
    }
}
