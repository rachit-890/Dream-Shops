package org.dailycodework.dreamshops.service.order;

import org.dailycodework.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrder(Long id);

    List<Order> getUserOrders(Long userId);
}
