package vn.iotstar.service;

import java.util.List;
import vn.iotstar.model.User_24162144;

public interface IUserService_24162144 {
    User_24162144 findById(int id);
    User_24162144 findByEmail(String email);
    boolean checkExistEmail(String email);
    List<User_24162144> findAll();
    void insert(User_24162144 user);
    void update(User_24162144 user);
    void delete(int id);
    User_24162144 login(String email, String password);
    void updateLastLogin(int userId);
}
