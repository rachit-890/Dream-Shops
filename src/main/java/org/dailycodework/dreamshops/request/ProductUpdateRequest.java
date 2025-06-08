package org.dailycodework.dreamshops.request;

import lombok.Data;
import org.dailycodework.dreamshops.model.Category;

import java.math.BigDecimal;
@Data
public class ProductUpdateRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
