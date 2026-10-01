package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IBookDao_24162144;
import vn.iotstar.dao.impl.BookDaoImpl_24162144;
import vn.iotstar.model.Book_24162144;
import vn.iotstar.service.IBookService_24162144;

public class BookServiceImpl_24162144 implements IBookService_24162144 {
    private IBookDao_24162144 bookDao = new BookDaoImpl_24162144();

    @Override
    public List<Book_24162144> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Book_24162144 findById(int bookId) {
        return bookDao.findById(bookId);
    }

    @Override
    public List<Book_24162144> getBooksByAuthorWithPaging(int authorId, int page, int pageSize) {
        return bookDao.getBooksByAuthorWithPaging(authorId, page, pageSize);
    }

    @Override
    public int countBooksByAuthor(int authorId) {
        return bookDao.countBooksByAuthor(authorId);
    }

    @Override
    public void insert(Book_24162144 book, int authorId) {
        bookDao.insert(book, authorId);
    }

    @Override
    public void update(Book_24162144 book, int authorId) {
        bookDao.update(book, authorId);
    }

    @Override
    public void delete(int bookId) {
        bookDao.delete(bookId);
    }
}
