package org.dailycodework.dreamshops.service.cart;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Cart;
import org.dailycodework.dreamshops.model.User;
import org.dailycodework.dreamshops.repository.CartItemRepository;
import org.dailycodework.dreamshops.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }





    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
       return cart.getTotalAmount();
    }
    @Override
    public Cart initializeNewCart(User user) {

        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

}
