package vn.iotstar.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import vn.iotstar.model.Book_24162144;

public interface IBookDao_24162144 {
    List<Book_24162144> findAll();
    Book_24162144 findById(int bookId);
    List<Book_24162144> getBooksByAuthorWithPaging(int authorId, int page, int pageSize);
    int countBooksByAuthor(int authorId);
    void insert(Book_24162144 book, int authorId);
    void update(Book_24162144 book, int authorId);
    void delete(int bookId);
    boolean deductStock(Connection conn, int bookId, int quantity) throws SQLException;
    boolean restoreStock(Connection conn, int bookId, int quantity) throws SQLException;
}
