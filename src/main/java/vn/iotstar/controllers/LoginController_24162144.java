package vn.iotstar.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IUserService_24162144;
import vn.iotstar.service.impl.UserServiceImpl_24162144;

@WebServlet(urlPatterns = {"/login"})
public class LoginController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162144 userService = new UserServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("account") != null) {
            User_24162144 user = (User_24162144) session.getAttribute("account");
            if (user.isAdmin() || user.isIs_admin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/books");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
            return;
        }

        if (req.getParameter("registerSuccess") != null) {
            req.setAttribute("success", "Kích hoạt tài khoản thành công! Vui lòng đăng nhập.");
        }

        req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ Email và Mật khẩu!");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
            return;
        }

        User_24162144 user = userService.login(email.trim(), password.trim());

        if (user != null) {
            // Đăng nhập thành công -> Lưu vào Session
            HttpSession session = req.getSession(true);
            session.setAttribute("account", user);

            // Cập nhật last_login
            userService.updateLastLogin(user.getId());

            // Phân quyền điều hướng: Admin vào /admin/books, User vào /home
            if (user.isAdmin() || user.isIs_admin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/books");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            // Đăng nhập thất bại: Quay lại trang đăng nhập kèm thông báo lỗi
            req.setAttribute("error", "Tài khoản hoặc mật khẩu không chính xác!");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/web/login.jsp").forward(req, resp);
        }
    }
}
