package org.example.petstore.persistence;

import org.example.petstore.domain.Cart;
import org.example.petstore.domain.Item;

public interface CartDao {
    public int createCart(String username);
    public Cart getCartByUsername(String username);
    public void addItemToCart(int cartId, Item item, boolean inStock);
    public void updateItemQuantity(String username, String itemId, int quantity);
    public void removeItemFromCart(String username, String itemId);
    public void clearCart(int cartId);
}
