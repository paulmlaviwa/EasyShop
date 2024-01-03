package org.yearup.data;

import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCartItem addCart(int userId, int productId, ShoppingCartItem shoppingCartItem);

}
