package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.IAuthorDao_24162144;
import vn.iotstar.dao.impl.AuthorDaoImpl_24162144;
import vn.iotstar.model.Author_24162144;
import vn.iotstar.service.IAuthorService_24162144;

public class AuthorServiceImpl_24162144 implements IAuthorService_24162144 {
    private IAuthorDao_24162144 authorDao = new AuthorDaoImpl_24162144();

    @Override
    public Author_24162144 findById(int id) {
        return authorDao.findById(id);
    }

    @Override
    public List<Author_24162144> findAll() {
        return authorDao.findAll();
    }

    @Override
    public void insert(Author_24162144 author) {
        authorDao.insert(author);
    }

    @Override
    public void update(Author_24162144 author) {
        authorDao.update(author);
    }

    @Override
    public void delete(int id) {
        authorDao.delete(id);
    }
}
