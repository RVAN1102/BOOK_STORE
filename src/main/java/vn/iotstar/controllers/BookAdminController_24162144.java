package vn.iotstar.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import vn.iotstar.model.Author_24162144;
import vn.iotstar.model.Book_24162144;
import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IAuthorService_24162144;
import vn.iotstar.service.IBookService_24162144;
import vn.iotstar.service.impl.AuthorServiceImpl_24162144;
import vn.iotstar.service.impl.BookServiceImpl_24162144;

@WebServlet(urlPatterns = {"/admin/books", "/admin/books/add", "/admin/books/edit", "/admin/books/delete"})
public class BookAdminController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IBookService_24162144 bookService = new BookServiceImpl_24162144();
    private IAuthorService_24162144 authorService = new AuthorServiceImpl_24162144();

    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        User_24162144 user = (session != null) ? (User_24162144) session.getAttribute("account") : null;
        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (!checkAdmin(req, resp)) {
            return;
        }

        String path = req.getServletPath();

        if ("/admin/books/add".equals(path)) {
            List<Author_24162144> authors = authorService.findAll();
            req.setAttribute("authors", authors);
            req.setAttribute("isAdd", true);
            req.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(req, resp);
        } else if ("/admin/books/edit".equals(path)) {
            int bookId = parseId(req.getParameter("id"));
            Book_24162144 book = bookService.findById(bookId);
            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/books");
                return;
            }
            List<Author_24162144> authors = authorService.findAll();
            req.setAttribute("book", book);
            req.setAttribute("authors", authors);
            req.setAttribute("isAdd", false);
            req.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(req, resp);
        } else if ("/admin/books/delete".equals(path)) {
            int bookId = parseId(req.getParameter("id"));
            if (bookId > 0) {
                bookService.delete(bookId);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } else {
            // Danh sách tất cả sách: /admin/books
            List<Book_24162144> books = bookService.findAll();
            req.setAttribute("books", books);
            req.getRequestDispatcher("/WEB-INF/views/admin/book-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        if (!checkAdmin(req, resp)) {
            return;
        }

        String path = req.getServletPath();

        if ("/admin/books/add".equals(path)) {
            Book_24162144 book = extractBookFromRequest(req);
            int authorId = parseId(req.getParameter("authorId"));
            bookService.insert(book, authorId);
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } else if ("/admin/books/edit".equals(path)) {
            Book_24162144 book = extractBookFromRequest(req);
            int bookId = parseId(req.getParameter("bookid"));
            book.setBookid(bookId);
            int authorId = parseId(req.getParameter("authorId"));
            bookService.update(book, authorId);
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } else {
            doGet(req, resp);
        }
    }

    private Book_24162144 extractBookFromRequest(HttpServletRequest req) {
        Book_24162144 book = new Book_24162144();
        book.setIsbn(parseId(req.getParameter("isbn")));
        book.setTitle(req.getParameter("title") != null ? req.getParameter("title").trim() : "");
        book.setPublisher(req.getParameter("publisher") != null ? req.getParameter("publisher").trim() : "");

        double price = 0.0;
        try {
            String priceParam = req.getParameter("price");
            if (priceParam != null && !priceParam.trim().isEmpty()) {
                price = Double.parseDouble(priceParam.trim());
            }
        } catch (Exception ignored) {}
        book.setPrice(price);

        book.setDescription(req.getParameter("description") != null ? req.getParameter("description").trim() : "");

        Date publishDate = null;
        try {
            String dateParam = req.getParameter("publish_date");
            if (dateParam != null && !dateParam.trim().isEmpty()) {
                publishDate = Date.valueOf(dateParam.trim());
            }
        } catch (Exception ignored) {}
        if (publishDate == null) {
            publishDate = new Date(System.currentTimeMillis());
        }
        book.setPublish_date(publishDate);

        String coverImage = req.getParameter("cover_image");
        if (coverImage == null || coverImage.trim().isEmpty()) {
            coverImage = "default-book.jpg";
        }
        book.setCover_image(coverImage.trim());

        int quantity = 0;
        try {
            String qtyParam = req.getParameter("quantity");
            if (qtyParam != null && !qtyParam.trim().isEmpty()) {
                quantity = Integer.parseInt(qtyParam.trim());
            }
        } catch (Exception ignored) {}
        book.setQuantity(quantity);

        return book;
    }

    private int parseId(String val) {
        if (val == null || val.trim().isEmpty()) return 0;
        try {
            return Integer.parseInt(val.trim());
        } catch (Exception e) {
            return 0;
        }
    }
}
