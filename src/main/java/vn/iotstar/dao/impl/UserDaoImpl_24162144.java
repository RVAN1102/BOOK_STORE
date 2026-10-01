package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.IUserDao_24162144;
import vn.iotstar.model.User_24162144;

public class UserDaoImpl_24162144 extends DBConnection implements IUserDao_24162144 {

    private User_24162144 mapResultSetToUser(ResultSet rs) throws Exception {
        User_24162144 user = new User_24162144();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setFullname(rs.getString("fullname"));
        user.setPhone(rs.getInt("phone"));
        user.setPasswd(rs.getString("passwd"));
        user.setSignup_date(rs.getTimestamp("signup_date"));
        user.setLast_login(rs.getTimestamp("last_login"));
        user.setIs_admin(rs.getBoolean("is_admin"));
        return user;
    }

    @Override
    public User_24162144 findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User_24162144 findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean checkExistEmail(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User_24162144> findAll() {
        List<User_24162144> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void insert(User_24162144 user) {
        String sql = "INSERT INTO users(email, fullname, phone, passwd, signup_date, is_admin) VALUES (?, ?, ?, ?, GETDATE(), ?)";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullname());
            ps.setInt(3, user.getPhone());
            ps.setString(4, user.getPasswd());
            ps.setBoolean(5, user.isIs_admin());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User_24162144 user) {
        String sql = "UPDATE users SET email = ?, fullname = ?, phone = ?, passwd = ?, is_admin = ? WHERE id = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFullname());
            ps.setInt(3, user.getPhone());
            ps.setString(4, user.getPasswd());
            ps.setBoolean(5, user.isIs_admin());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User_24162144 login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND passwd = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateLastLogin(int userId) {
        String sql = "UPDATE users SET last_login = GETDATE() WHERE id = ?";
        try (Connection conn = super.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
