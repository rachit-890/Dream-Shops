package org.dailycodework.dreamshops.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private Integer quantity;
    private String productName;
    private BigDecimal price;
}
