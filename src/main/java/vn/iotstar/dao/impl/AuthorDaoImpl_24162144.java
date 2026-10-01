package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.IAuthorDao_24162144;
import vn.iotstar.model.Author_24162144;

public class AuthorDaoImpl_24162144 extends DBConnection implements IAuthorDao_24162144 {

    @Override
    public List<Author_24162144> findAll() {
        List<Author_24162144> list = new ArrayList<>();
        String sql = "SELECT author_id, author_name, date_of_birth FROM dbo.author ORDER BY author_id ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Author_24162144 author = new Author_24162144();
                author.setAuthor_id(rs.getInt("author_id"));
                author.setAuthor_name(rs.getString("author_name"));
                author.setDate_of_birth(rs.getDate("date_of_birth"));
                list.add(author);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Author_24162144 findById(int id) {
        String sql = "SELECT author_id, author_name, date_of_birth FROM dbo.author WHERE author_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Author_24162144 author = new Author_24162144();
                    author.setAuthor_id(rs.getInt("author_id"));
                    author.setAuthor_name(rs.getString("author_name"));
                    author.setDate_of_birth(rs.getDate("date_of_birth"));
                    return author;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void insert(Author_24162144 author) {
        String sql = "INSERT INTO dbo.author(author_name, date_of_birth) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getAuthor_name());
            ps.setDate(2, author.getDate_of_birth());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Author_24162144 author) {
        String sql = "UPDATE dbo.author SET author_name = ?, date_of_birth = ? WHERE author_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getAuthor_name());
            ps.setDate(2, author.getDate_of_birth());
            ps.setInt(3, author.getAuthor_id());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM dbo.author WHERE author_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
