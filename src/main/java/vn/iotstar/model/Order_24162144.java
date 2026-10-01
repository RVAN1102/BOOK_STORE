package vn.iotstar.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order_24162144 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int orderId;
    private int userId;
    private String fullname;
    private String phone;
    private String address;
    private String note;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private Timestamp createdAt;
    private List<OrderDetail_24162144> items = new ArrayList<>();

    public Order_24162144() {
    }

    public Order_24162144(int orderId, int userId, String fullname, String phone, String address, String note,
            double totalAmount, String paymentMethod, String status, Timestamp createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.note = note;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = createdAt;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotal_amount() {
        return totalAmount;
    }

    public void setTotal_amount(double total_amount) {
        this.totalAmount = total_amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPayment_method() {
        return paymentMethod;
    }

    public void setPayment_method(String payment_method) {
        this.paymentMethod = payment_method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(Timestamp created_at) {
        this.createdAt = created_at;
    }

    public List<OrderDetail_24162144> getItems() {
        return items;
    }

    public void setItems(List<OrderDetail_24162144> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order_24162144 [orderId=" + orderId + ", userId=" + userId + ", fullname=" + fullname + ", phone="
                + phone + ", totalAmount=" + totalAmount + ", paymentMethod=" + paymentMethod + ", status=" + status
                + ", createdAt=" + createdAt + ", itemsCount=" + (items != null ? items.size() : 0) + "]";
    }
}
