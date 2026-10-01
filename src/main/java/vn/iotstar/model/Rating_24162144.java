package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Rating_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int userid;
    private int bookid;
    private int rating;
    private String review_text;
    private Timestamp created_at;

    // Trường bổ trợ theo yêu cầu đề thi
    private String userFullname;
    private String book_title;

    public Rating_24162144() {
    }

    public Rating_24162144(int userid, int bookid, int rating, String review_text) {
        this.userid = userid;
        this.bookid = bookid;
        this.rating = rating;
        this.review_text = review_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getUser_id() {
        return userid;
    }

    public void setUser_id(int user_id) {
        this.userid = user_id;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userId) {
        this.userid = userId;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getBookId() {
        return bookid;
    }

    public void setBookId(int bookId) {
        this.bookid = bookId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String review_text) {
        this.review_text = review_text;
    }

    public String getReviewText() {
        return review_text;
    }

    public void setReviewText(String reviewText) {
        this.review_text = reviewText;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.created_at = createdAt;
    }

    public String getUserFullname() {
        return userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }

    public String getUser_fullname() {
        return userFullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.userFullname = user_fullname;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    @Override
    public String toString() {
        return "Rating_24162144 [userid=" + userid + ", bookid=" + bookid + ", rating=" + rating + ", userFullname="
                + userFullname + ", created_at=" + created_at + "]";
    }
}
