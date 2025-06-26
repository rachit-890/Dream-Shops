package org.dailycodework.dreamshops.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private List<OrderDto> orders;
    private CartDto cart;
}
