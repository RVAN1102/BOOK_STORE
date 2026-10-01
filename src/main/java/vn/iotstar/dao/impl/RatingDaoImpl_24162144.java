package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.IRatingDao_24162144;
import vn.iotstar.model.Rating_24162144;

public class RatingDaoImpl_24162144 extends DBConnection implements IRatingDao_24162144 {

    @Override
    public List<Rating_24162144> findByBookId(int bookId) {
        List<Rating_24162144> list = new ArrayList<>();
        String sql = "SELECT r.userid, r.bookid, r.rating, r.review_text, r.created_at, u.fullname AS userFullname "
                   + "FROM dbo.rating r "
                   + "JOIN dbo.users u ON r.userid = u.id "
                   + "WHERE r.bookid = ? "
                   + "ORDER BY r.created_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Rating_24162144 rating = new Rating_24162144();
                    rating.setUserid(rs.getInt("userid"));
                    rating.setBookid(rs.getInt("bookid"));
                    rating.setRating(rs.getInt("rating"));
                    rating.setReview_text(rs.getString("review_text"));
                    rating.setCreated_at(rs.getTimestamp("created_at"));
                    rating.setUserFullname(rs.getString("userFullname"));
                    list.add(rating);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countByBookId(int bookId) {
        String sql = "SELECT COUNT(*) FROM dbo.rating WHERE bookid = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
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
    public double getAvgRatingByBookId(int bookId) {
        String sql = "SELECT ISNULL(AVG(CAST(rating AS FLOAT)), 0.0) FROM dbo.rating WHERE bookid = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Math.round(rs.getDouble(1) * 10.0) / 10.0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    @Override
    public void insert(Rating_24162144 rating) {
        String sql = "IF EXISTS (SELECT 1 FROM dbo.rating WHERE userid = ? AND bookid = ?) "
                   + "    UPDATE dbo.rating SET rating = ?, review_text = ?, created_at = GETDATE() WHERE userid = ? AND bookid = ? "
                   + "ELSE "
                   + "    INSERT INTO dbo.rating(userid, bookid, rating, review_text, created_at) VALUES (?, ?, ?, ?, GETDATE())";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, rating.getUserid());
            ps.setInt(2, rating.getBookid());

            ps.setInt(3, rating.getRating());
            ps.setString(4, rating.getReview_text());
            ps.setInt(5, rating.getUserid());
            ps.setInt(6, rating.getBookid());

            ps.setInt(7, rating.getUserid());
            ps.setInt(8, rating.getBookid());
            ps.setInt(9, rating.getRating());
            ps.setString(10, rating.getReview_text());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
