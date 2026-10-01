package vn.iotstar.model;

import java.io.Serializable;

public class CartItem_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private Book_24162144 book;
    private int quantity;

    public CartItem_24162144() {
    }

    public CartItem_24162144(Book_24162144 book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book_24162144 getBook() {
        return book;
    }

    public void setBook(Book_24162144 book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return book != null ? book.getPrice() * quantity : 0;
    }

    @Override
    public String toString() {
        return "CartItem_24162144 [book=" + (book != null ? book.getTitle() : "null") + ", quantity=" + quantity
                + ", totalPrice=" + getTotalPrice() + "]";
    }
}
