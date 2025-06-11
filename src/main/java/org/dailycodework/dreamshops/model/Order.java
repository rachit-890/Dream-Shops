package org.dailycodework.dreamshops.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dailycodework.dreamshops.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "orderItem",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
