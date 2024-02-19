package kz.aitu.se2311.oopproject.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import kz.aitu.se2311.oopproject.entities.Cart;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.responses.CartResponse;
import kz.aitu.se2311.oopproject.services.CartService;
import kz.aitu.se2311.oopproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Cart controller")
public class CartController {
    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<CartResponse> getCart() {
        User user = userService.getCurrentUser();
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(new CartResponse(cart));
    }
}
