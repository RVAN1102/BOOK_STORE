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

@WebServlet(urlPatterns = {"/verify-otp"})
public class VerifyOtpController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService_24162144 userService = new UserServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("tempUser") == null || session.getAttribute("otp") == null) {
            resp.sendRedirect(req.getContextPath() + "/register");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("tempUser") == null || session.getAttribute("otp") == null) {
            req.setAttribute("error", "Phiên xác thực đã hết hạn. Vui lòng đăng ký lại!");
            req.getRequestDispatcher("/WEB-INF/views/web/register.jsp").forward(req, resp);
            return;
        }

        String inputOtp = req.getParameter("otp");
        String sessionOtp = (String) session.getAttribute("otp");
        User_24162144 tempUser = (User_24162144) session.getAttribute("tempUser");

        if (inputOtp != null && inputOtp.trim().equals(sessionOtp)) {
            // OTP chính xác -> Thêm tài khoản vào CSDL
            userService.insert(tempUser);

            // Dọn dẹp session tạm
            session.removeAttribute("tempUser");
            session.removeAttribute("otp");

            resp.sendRedirect(req.getContextPath() + "/login?registerSuccess=true");
        } else {
            req.setAttribute("error", "Mã OTP không chính xác hoặc đã hết hạn!");
            req.getRequestDispatcher("/WEB-INF/views/web/verify-otp.jsp").forward(req, resp);
        }
    }
}
