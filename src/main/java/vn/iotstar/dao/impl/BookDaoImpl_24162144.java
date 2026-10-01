package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.IBookDao_24162144;
import vn.iotstar.model.Book_24162144;

public class BookDaoImpl_24162144 extends DBConnection implements IBookDao_24162144 {

    @Override
    public List<Book_24162144> findAll() {
        List<Book_24162144> list = new ArrayList<>();
        String sql = "SELECT b.bookid, b.isbn, b.title, b.publisher, b.price, b.description, b.publish_date, b.cover_image, b.quantity, "
                   + "       a.author_id, a.author_name, "
                   + "       (SELECT COUNT(*) FROM dbo.rating r WHERE r.bookid = b.bookid) AS review_count "
                   + "FROM dbo.books b "
                   + "LEFT JOIN dbo.book_author ba ON b.bookid = ba.bookid "
                   + "LEFT JOIN dbo.author a ON ba.author_id = a.author_id "
                   + "ORDER BY b.bookid DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Book_24162144 book = mapResultSetToBook(rs);
                list.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Book_24162144 findById(int bookId) {
        String sql = "SELECT b.bookid, b.isbn, b.title, b.publisher, b.price, b.description, b.publish_date, b.cover_image, b.quantity, "
                   + "       a.author_id, a.author_name, "
                   + "       (SELECT COUNT(*) FROM dbo.rating r WHERE r.bookid = b.bookid) AS review_count, "
                   + "       (SELECT ISNULL(AVG(CAST(r.rating AS FLOAT)), 0.0) FROM dbo.rating r WHERE r.bookid = b.bookid) AS avg_rating "
                   + "FROM dbo.books b "
                   + "LEFT JOIN dbo.book_author ba ON b.bookid = ba.bookid "
                   + "LEFT JOIN dbo.author a ON ba.author_id = a.author_id "
                   + "WHERE b.bookid = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Book_24162144 book = mapResultSetToBook(rs);
                    book.setAvgRating(Math.round(rs.getDouble("avg_rating") * 10.0) / 10.0);
                    return book;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Book_24162144> getBooksByAuthorWithPaging(int authorId, int page, int pageSize) {
        List<Book_24162144> list = new ArrayList<>();
        int offset = Math.max(0, (page - 1) * pageSize);
        String sql = "SELECT b.bookid, b.isbn, b.title, b.publisher, b.price, b.description, b.publish_date, b.cover_image, b.quantity, "
                   + "       a.author_id, a.author_name, "
                   + "       (SELECT COUNT(*) FROM dbo.rating r WHERE r.bookid = b.bookid) AS review_count "
                   + "FROM dbo.books b "
                   + "JOIN dbo.book_author ba ON b.bookid = ba.bookid "
                   + "JOIN dbo.author a ON ba.author_id = a.author_id "
                   + "WHERE a.author_id = ? "
                   + "ORDER BY b.bookid ASC "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Book_24162144 book = mapResultSetToBook(rs);
                    list.add(book);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countBooksByAuthor(int authorId) {
        String sql = "SELECT COUNT(*) FROM dbo.books b "
                   + "JOIN dbo.book_author ba ON b.bookid = ba.bookid "
                   + "WHERE ba.author_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, authorId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void insert(Book_24162144 book, int authorId) {
        String sqlBook = "INSERT INTO dbo.books(isbn, title, publisher, price, description, publish_date, cover_image, quantity) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlBa = "INSERT INTO dbo.book_author(bookid, author_id) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            int newBookId = -1;
            try (PreparedStatement psBook = conn.prepareStatement(sqlBook, Statement.RETURN_GENERATED_KEYS)) {
                psBook.setInt(1, book.getIsbn());
                psBook.setString(2, book.getTitle());
                psBook.setString(3, book.getPublisher());
                psBook.setDouble(4, book.getPrice());
                psBook.setString(5, book.getDescription());
                psBook.setDate(6, book.getPublish_date());
                psBook.setString(7, book.getCover_image());
                psBook.setInt(8, book.getQuantity());
                psBook.executeUpdate();

                try (ResultSet rsKey = psBook.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        newBookId = rsKey.getInt(1);
                    }
                }
            }

            if (newBookId > 0 && authorId > 0) {
                try (PreparedStatement psBa = conn.prepareStatement(sqlBa)) {
                    psBa.setInt(1, newBookId);
                    psBa.setInt(2, authorId);
                    psBa.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book_24162144 book, int authorId) {
        String sqlBook = "UPDATE dbo.books SET isbn = ?, title = ?, publisher = ?, price = ?, description = ?, "
                       + "publish_date = ?, cover_image = ?, quantity = ? WHERE bookid = ?";
        String sqlBa = "IF EXISTS (SELECT 1 FROM dbo.book_author WHERE bookid = ?) "
                     + "    UPDATE dbo.book_author SET author_id = ? WHERE bookid = ? "
                     + "ELSE "
                     + "    INSERT INTO dbo.book_author(bookid, author_id) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psBook = conn.prepareStatement(sqlBook)) {
                psBook.setInt(1, book.getIsbn());
                psBook.setString(2, book.getTitle());
                psBook.setString(3, book.getPublisher());
                psBook.setDouble(4, book.getPrice());
                psBook.setString(5, book.getDescription());
                psBook.setDate(6, book.getPublish_date());
                psBook.setString(7, book.getCover_image());
                psBook.setInt(8, book.getQuantity());
                psBook.setInt(9, book.getBookid());
                psBook.executeUpdate();
            }

            if (authorId > 0) {
                try (PreparedStatement psBa = conn.prepareStatement(sqlBa)) {
                    psBa.setInt(1, book.getBookid());
                    psBa.setInt(2, authorId);
                    psBa.setInt(3, book.getBookid());
                    psBa.setInt(4, book.getBookid());
                    psBa.setInt(5, authorId);
                    psBa.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int bookId) {
        String sqlRating = "DELETE FROM dbo.rating WHERE bookid = ?";
        String sqlBa = "DELETE FROM dbo.book_author WHERE bookid = ?";
        String sqlBook = "DELETE FROM dbo.books WHERE bookid = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psRating = conn.prepareStatement(sqlRating)) {
                psRating.setInt(1, bookId);
                psRating.executeUpdate();
            }
            try (PreparedStatement psBa = conn.prepareStatement(sqlBa)) {
                psBa.setInt(1, bookId);
                psBa.executeUpdate();
            }
            try (PreparedStatement psBook = conn.prepareStatement(sqlBook)) {
                psBook.setInt(1, bookId);
                psBook.executeUpdate();
            }
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deductStock(Connection conn, int bookId, int quantity) throws SQLException {
        String sql = "UPDATE dbo.books SET quantity = quantity - ? WHERE bookid = ? AND quantity >= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, bookId);
            ps.setInt(3, quantity);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean restoreStock(Connection conn, int bookId, int quantity) throws SQLException {
        String sql = "UPDATE dbo.books SET quantity = quantity + ? WHERE bookid = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, bookId);
            return ps.executeUpdate() > 0;
        }
    }

    private Book_24162144 mapResultSetToBook(ResultSet rs) throws Exception {
        Book_24162144 book = new Book_24162144();
        book.setBookid(rs.getInt("bookid"));
        book.setIsbn(rs.getInt("isbn"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setPrice(rs.getDouble("price"));
        book.setDescription(rs.getString("description"));
        book.setPublish_date(rs.getDate("publish_date"));
        book.setCover_image(rs.getString("cover_image"));
        book.setQuantity(rs.getInt("quantity"));

        try {
            book.setAuthorId(rs.getInt("author_id"));
            book.setAuthorName(rs.getString("author_name"));
        } catch (Exception ignored) {}

        try {
            book.setReviewCount(rs.getInt("review_count"));
        } catch (Exception ignored) {}

        return book;
    }
}
