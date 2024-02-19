package kz.aitu.se2311.oopproject.responses;

import kz.aitu.se2311.oopproject.entities.Cart;
import lombok.Data;

@Data
public class CartResponse {
    private String username;
    public CartResponse(Cart cart) {
        setUsername(cart.getOwner().getUsername());
    }
}
