package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.Author_24162144;

public interface IAuthorDao_24162144 {
    Author_24162144 findById(int id);
    List<Author_24162144> findAll();
    void insert(Author_24162144 author);
    void update(Author_24162144 author);
    void delete(int id);
}
