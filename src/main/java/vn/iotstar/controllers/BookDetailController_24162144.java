package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.Book_24162144;
import vn.iotstar.model.Rating_24162144;
import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IBookService_24162144;
import vn.iotstar.service.IRatingService_24162144;
import vn.iotstar.service.impl.BookServiceImpl_24162144;
import vn.iotstar.service.impl.RatingServiceImpl_24162144;

@WebServlet(urlPatterns = {"/book/detail"})
public class BookDetailController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IBookService_24162144 bookService = new BookServiceImpl_24162144();
    private IRatingService_24162144 ratingService = new RatingServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        int bookId = 0;
        try {
            bookId = Integer.parseInt(idParam.trim());
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        Book_24162144 book = bookService.findById(bookId);
        if (book == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        List<Rating_24162144> ratings = ratingService.findByBookId(bookId);
        double avgRating = ratingService.getAvgRatingByBookId(bookId);
        int reviewCount = ratingService.countByBookId(bookId);

        book.setAvgRating(avgRating);
        book.setReviewCount(reviewCount);

        req.setAttribute("book", book);
        req.setAttribute("ratings", ratings);
        req.setAttribute("avgRating", avgRating);
        req.setAttribute("reviewCount", reviewCount);

        req.getRequestDispatcher("/WEB-INF/views/web/book-detail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 1. Kiểm tra xác thực session đăng nhập
        HttpSession session = req.getSession(false);
        User_24162144 user = (session != null) ? (User_24162144) session.getAttribute("account") : null;
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 2. Nhận dữ liệu đánh giá
        String bookIdParam = req.getParameter("bookId");
        String ratingParam = req.getParameter("rating");
        String reviewText = req.getParameter("review_text");

        int bookId = 0;
        try {
            bookId = Integer.parseInt(bookIdParam.trim());
        } catch (Exception ignored) {}

        if (bookId <= 0) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        int ratingValue = 5;
        try {
            if (ratingParam != null && !ratingParam.trim().isEmpty()) {
                ratingValue = Integer.parseInt(ratingParam.trim());
                if (ratingValue < 1) ratingValue = 1;
                if (ratingValue > 5) ratingValue = 5;
            }
        } catch (Exception ignored) {}

        Rating_24162144 rating = new Rating_24162144();
        rating.setUserid(user.getId());
        rating.setBookid(bookId);
        rating.setRating(ratingValue);
        rating.setReview_text(reviewText != null ? reviewText.trim() : "");

        ratingService.insert(rating);

        resp.sendRedirect(req.getContextPath() + "/book/detail?id=" + bookId);
    }
}
