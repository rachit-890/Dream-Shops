package org.dailycodework.dreamshops.controller;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.dto.OrderDto;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Order;
import org.dailycodework.dreamshops.model.OrderItem;
import org.dailycodework.dreamshops.response.ApiResponse;
import org.dailycodework.dreamshops.service.order.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order=orderService.placeOrder(userId);
            OrderDto orderDto=orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order created successfully",orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body(new ApiResponse("error Occured",e.getMessage()));
        }

    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
           OrderDto order=orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order found",order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }

    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUsersOrders(@PathVariable Long userId){
        try {
            List<OrderDto> orders=orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Orders found",orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }

    }


}
