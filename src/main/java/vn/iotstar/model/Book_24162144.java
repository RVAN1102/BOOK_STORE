package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Date;

public class Book_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    // Các trường gốc trong database
    private int bookid;
    private int isbn;
    private String title;
    private String publisher;
    private double price;
    private String description;
    private Date publish_date;
    private String cover_image;
    private int quantity;

    // Các trường mở rộng theo yêu cầu đề thi
    private int authorId;
    private String authorName;
    private int reviewCount;
    private double avgRating;

    public Book_24162144() {
    }

    public Book_24162144(int bookid, int isbn, String title, String publisher, double price, String description,
            Date publish_date, String cover_image, int quantity) {
        this.bookid = bookid;
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.price = price;
        this.description = description;
        this.publish_date = publish_date;
        this.cover_image = cover_image;
        this.quantity = quantity;
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

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public Date getPublishDate() {
        return publish_date;
    }

    public void setPublishDate(Date publishDate) {
        this.publish_date = publishDate;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getCoverImage() {
        return cover_image;
    }

    public void setCoverImage(String coverImage) {
        this.cover_image = coverImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getAuthor_id() {
        return authorId;
    }

    public void setAuthor_id(int author_id) {
        this.authorId = author_id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthor_name() {
        return authorName;
    }

    public void setAuthor_name(String author_name) {
        this.authorName = author_name;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getReview_count() {
        return reviewCount;
    }

    public void setReview_count(int review_count) {
        this.reviewCount = review_count;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public double getAvg_rating() {
        return avgRating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avgRating = avg_rating;
    }

    @Override
    public String toString() {
        return "Book_24162144 [bookid=" + bookid + ", isbn=" + isbn + ", title=" + title + ", publisher=" + publisher
                + ", price=" + price + ", authorName=" + authorName + ", reviewCount=" + reviewCount + ", avgRating="
                + avgRating + "]";
    }
}
