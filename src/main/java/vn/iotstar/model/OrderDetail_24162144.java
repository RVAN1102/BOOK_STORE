package vn.iotstar.model;

import java.io.Serializable;

public class OrderDetail_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int orderId;
    private int bookId;
    private double price;
    private int quantity;
    private Book_24162144 book;

    public OrderDetail_24162144() {
    }

    public OrderDetail_24162144(int id, int orderId, int bookId, double price, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
    }

    public OrderDetail_24162144(int id, int orderId, int bookId, double price, int quantity, Book_24162144 book) {
        this.id = id;
        this.orderId = orderId;
        this.bookId = bookId;
        this.price = price;
        this.quantity = quantity;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrder_id() {
        return orderId;
    }

    public void setOrder_id(int order_id) {
        this.orderId = order_id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getBookid() {
        return bookId;
    }

    public void setBookid(int bookid) {
        this.bookId = bookid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book_24162144 getBook() {
        return book;
    }

    public void setBook(Book_24162144 book) {
        this.book = book;
    }

    public double getTotalPrice() {
        return price * quantity;
    }

    public double getTotal_price() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail_24162144 [id=" + id + ", orderId=" + orderId + ", bookId=" + bookId + ", price=" + price
                + ", quantity=" + quantity + ", book=" + (book != null ? book.getTitle() : "null") + "]";
    }
}
