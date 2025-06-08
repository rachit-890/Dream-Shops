package org.dailycodework.dreamshops.service.cart;

import org.dailycodework.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, int quantity, Long productId);
    void removeItemFromCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
