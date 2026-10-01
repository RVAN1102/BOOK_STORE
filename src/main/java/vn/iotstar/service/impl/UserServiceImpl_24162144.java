package vn.iotstar.service.impl;

import java.util.List;
import vn.iotstar.dao.IUserDao_24162144;
import vn.iotstar.dao.impl.UserDaoImpl_24162144;
import vn.iotstar.model.User_24162144;
import vn.iotstar.service.IUserService_24162144;

public class UserServiceImpl_24162144 implements IUserService_24162144 {
    private IUserDao_24162144 userDao = new UserDaoImpl_24162144();

    @Override
    public User_24162144 findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User_24162144 findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDao.checkExistEmail(email);
    }

    @Override
    public List<User_24162144> findAll() {
        return userDao.findAll();
    }

    @Override
    public void insert(User_24162144 user) {
        userDao.insert(user);
    }

    @Override
    public void update(User_24162144 user) {
        userDao.update(user);
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User_24162144 login(String email, String password) {
        return userDao.login(email, password);
    }

    @Override
    public void updateLastLogin(int userId) {
        userDao.updateLastLogin(userId);
    }
}
