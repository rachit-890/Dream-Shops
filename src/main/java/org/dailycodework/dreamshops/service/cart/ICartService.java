package org.dailycodework.dreamshops.service.cart;

import org.dailycodework.dreamshops.model.Cart;
import org.dailycodework.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

   Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
