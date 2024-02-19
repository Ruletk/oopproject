package kz.aitu.se2311.oopproject.services;

import jakarta.transaction.Transactional;
import kz.aitu.se2311.oopproject.entities.Cart;
import kz.aitu.se2311.oopproject.entities.CartsGoods;
import kz.aitu.se2311.oopproject.entities.Good;
import kz.aitu.se2311.oopproject.entities.User;
import kz.aitu.se2311.oopproject.repositories.CartRepository;
import kz.aitu.se2311.oopproject.repositories.CartsGoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartsGoodsRepository cartsGoodsRepository;

    public Cart addGood(Cart cart, Good good, Boolean active, int quantity) {
        return addGoodToCart(cart, good, active, quantity);
    }

    public Cart editGood(Cart cart, Good good, Boolean active, int quantity) {
        return editGoodInCart(cart, good, active, quantity);
    }

    public Cart deleteGood(Cart cart, Good good) {
        return deleteGoodFromCart(cart, good);
    }

    public Cart setCartInactive(Cart cart) {
        cart.setVisible(false);
        return save(cart);
    }

    public Cart getCartByUser(User user) {
        Cart cart = cartRepository.getCartByOwnerAndVisibleIsTrue(user);
        if (cart == null)
            return createCart(user);
        return cart;
    }

    private Cart addGoodToCart(Cart cart, Good good, Boolean active, int quantity) {
        Collection<CartsGoods> collection = cart.getGoods();
        Optional<CartsGoods> optionalCartsGoods = collection.stream()
                .filter(cartsGoods -> cartsGoods.getGood().equals(good))
                .findFirst();
        CartsGoods cartsGoods;

        if (optionalCartsGoods.isPresent()) {
            cartsGoods = optionalCartsGoods.get();
            cartsGoods.setQuantity(cartsGoods.getQuantity()+1);
            save(cartsGoods);
        }
        else {
            cartsGoods = CartsGoods.builder().cart(cart).good(good).active(active).quantity(quantity).build();
            cartsGoods = save(cartsGoods);
            collection.add(cartsGoods);
        }

        cart.setGoods(collection);
        return cart;
    }

    private Cart editGoodInCart(Cart cart, Good good, Boolean active, int quantity) {
        Collection<CartsGoods> goods = cart.getGoods();
        for (CartsGoods cartGood : goods) {
            if (cartGood.getGood().equals(good)) {
                cartGood.setActive(active);
                cartGood.setQuantity(quantity);
                save(cartGood);
                break;
            }
        }
        return cart;
    }

    private Cart deleteGoodFromCart(Cart cart, Good good) {
        Collection<CartsGoods> goods = cart.getGoods();

        goods.removeIf(cartsGoods -> cartsGoods.getGood().equals(good));
        cart.setGoods(goods);
        return cart;
    }

    private Cart createCart(User user) {
        Cart cart = Cart.builder().visible(true).owner(user).build();
        return save(cart);
    }


    private Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    private CartsGoods save(CartsGoods cartsGoods) {
        return cartsGoodsRepository.save(cartsGoods);
    }
}
