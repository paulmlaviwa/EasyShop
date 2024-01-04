package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    ShoppingCart getByUserId(int userId);
    ShoppingCart addCart(int userId, int productId);
    ShoppingCart removeCart(int userId);

}
