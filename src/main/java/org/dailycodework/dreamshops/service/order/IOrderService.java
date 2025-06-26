package org.dailycodework.dreamshops.service.order;

import org.dailycodework.dreamshops.dto.OrderDto;
import org.dailycodework.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long id);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
