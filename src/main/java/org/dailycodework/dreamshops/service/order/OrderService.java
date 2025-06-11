package org.dailycodework.dreamshops.service.order;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.enums.OrderStatus;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Cart;
import org.dailycodework.dreamshops.model.Order;
import org.dailycodework.dreamshops.model.OrderItem;
import org.dailycodework.dreamshops.model.Product;
import org.dailycodework.dreamshops.repository.OrderRepository;
import org.dailycodework.dreamshops.repository.ProductRepository;
import org.dailycodework.dreamshops.service.cart.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private  final OrderRepository orderRepository;
    private  final ProductRepository productRepository;
    private final CartService cartService;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemsList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemsList));
        order.setTotalAmount(getTotalAmount(orderItemsList));
        Order savedOrder=  orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;

    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();

    }

    private BigDecimal getTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream()
                .map(item->item.getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getUserOrders(Long userId){
        return orderRepository.findByUserId(userId);
    }
}
