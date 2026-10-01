package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.model.OrderDetail_24162144;
import vn.iotstar.model.Order_24162144;

public interface IOrderDao_24162144 {
    int createOrder(Order_24162144 order, List<CartItem_24162144> items);
    List<Order_24162144> findOrdersByUserId(int userId, String status);
    Order_24162144 findOrderById(int orderId);
    List<OrderDetail_24162144> findDetailsByOrderId(int orderId);
    boolean cancelOrder(int orderId, int userId);
    boolean returnOrder(int orderId, int userId);
}
