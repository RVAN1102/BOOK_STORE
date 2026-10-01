package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.dao.IOrderDao_24162144;
import vn.iotstar.dao.impl.OrderDaoImpl_24162144;
import vn.iotstar.model.CartItem_24162144;
import vn.iotstar.model.OrderDetail_24162144;
import vn.iotstar.model.Order_24162144;
import vn.iotstar.service.IOrderService_24162144;

public class OrderServiceImpl_24162144 implements IOrderService_24162144 {

    private IOrderDao_24162144 orderDao = new OrderDaoImpl_24162144();

    @Override
    public int createOrder(Order_24162144 order, List<CartItem_24162144> items) {
        return orderDao.createOrder(order, items);
    }

    @Override
    public List<Order_24162144> findOrdersByUserId(int userId, String status) {
        return orderDao.findOrdersByUserId(userId, status);
    }

    @Override
    public Order_24162144 findOrderById(int orderId) {
        return orderDao.findOrderById(orderId);
    }

    @Override
    public List<OrderDetail_24162144> findDetailsByOrderId(int orderId) {
        return orderDao.findDetailsByOrderId(orderId);
    }

    @Override
    public boolean cancelOrder(int orderId, int userId) {
        return orderDao.cancelOrder(orderId, userId);
    }

    @Override
    public boolean returnOrder(int orderId, int userId) {
        return orderDao.returnOrder(orderId, userId);
    }
}
