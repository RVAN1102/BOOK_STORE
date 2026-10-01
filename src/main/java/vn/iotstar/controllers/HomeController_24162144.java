package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.iotstar.model.Author_24162144;
import vn.iotstar.model.Book_24162144;
import vn.iotstar.service.IAuthorService_24162144;
import vn.iotstar.service.IBookService_24162144;
import vn.iotstar.service.impl.AuthorServiceImpl_24162144;
import vn.iotstar.service.impl.BookServiceImpl_24162144;

@WebServlet(urlPatterns = {"/home", "/books"})
public class HomeController_24162144 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IAuthorService_24162144 authorService = new AuthorServiceImpl_24162144();
    private IBookService_24162144 bookService = new BookServiceImpl_24162144();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        List<Author_24162144> authors = authorService.findAll();

        int selectedAuthorId = 0;
        String authorIdParam = req.getParameter("authorId");
        if (authorIdParam != null && !authorIdParam.trim().isEmpty()) {
            try {
                selectedAuthorId = Integer.parseInt(authorIdParam.trim());
            } catch (NumberFormatException ignored) {}
        }

        Author_24162144 currentAuthor = null;
        if (!authors.isEmpty()) {
            if (selectedAuthorId > 0) {
                for (Author_24162144 a : authors) {
                    if (a.getAuthor_id() == selectedAuthorId) {
                        currentAuthor = a;
                        break;
                    }
                }
            }
            if (currentAuthor == null) {
                currentAuthor = authors.get(0);
                selectedAuthorId = currentAuthor.getAuthor_id();
            }
        }

        // 3. Phân trang: 3 cuốn sách mỗi trang
        int pageSize = 3;
        int currentPage = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam.trim());
            } catch (NumberFormatException ignored) {}
        }
        if (currentPage < 1) {
            currentPage = 1;
        }

        int totalBooks = 0;
        int totalPages = 1;
        List<Book_24162144> books = null;

        if (selectedAuthorId > 0) {
            totalBooks = bookService.countBooksByAuthor(selectedAuthorId);
            totalPages = (int) Math.ceil((double) totalBooks / pageSize);
            if (totalPages < 1) {
                totalPages = 1;
            }
            if (currentPage > totalPages) {
                currentPage = totalPages;
            }
            books = bookService.getBooksByAuthorWithPaging(selectedAuthorId, currentPage, pageSize);
        }

        // 4. Gửi dữ liệu ra view
        req.setAttribute("authors", authors);
        req.setAttribute("currentAuthor", currentAuthor);
        req.setAttribute("selectedAuthorId", selectedAuthorId);
        req.setAttribute("books", books);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("totalBooks", totalBooks);

        req.getRequestDispatcher("/WEB-INF/views/web/home.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
