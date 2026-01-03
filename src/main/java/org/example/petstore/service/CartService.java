package org.example.petstore.service;

import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Item;
import org.example.petstore.persistence.CartDao;
import org.example.petstore.persistence.impl.CartDaoImpl;

public class CartService {
    private CartDao cartDao= new CartDaoImpl();
    public int createCart(String username){
        return cartDao.createCart(username);
    }
    public Cart getCartByUsername(String username){
        return cartDao.getCartByUsername(username);
    }
    public void addItemToCart(int cartId, Item item, boolean inStock){
        cartDao.addItemToCart(cartId, item, inStock);
    }
    public void updateItemQuantity(String username, String itemId, int quantity){
        cartDao.updateItemQuantity(username, itemId, quantity);
    }

    public void removeItemFromCart(String username, String itemId){
        cartDao.removeItemFromCart(username, itemId);
    }
    public void clearCart(int cartId){
        cartDao.clearCart(cartId);
    }
}
