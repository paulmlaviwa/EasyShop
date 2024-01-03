package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCart extends MySqlDaoBase implements ShoppingCartDao {
    @Autowired
    public MySqlShoppingCart(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ShoppingCart getByUserId(int userId) {
        String query = " SELECT shopping_cart.quantity, products.* " +
                " FROM shopping_cart " +
                " JOIN products " +
                " ON products.product_id = shopping_cart.product_id " +
                " WHERE user_id = ? ";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();
            ShoppingCart shoppingCart = new ShoppingCart();

            while (result.next())
            {
                int productID = result.getInt("product_id");
                String name = result.getString("name");
                BigDecimal price = result.getBigDecimal("price");
                int category = result.getInt("category_id");
                String description = result.getString("description");
                String color = result.getString("color");
                int stock = result.getInt("stock");
                boolean isFeatured = result.getBoolean("featured");
                String imageUrl = result.getString("image_url");
                int quantity = result.getInt("quantity");
                Product product = new Product(productID, name, price, category, description, color, stock, isFeatured, imageUrl);
                ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
                shoppingCartItem.setProduct(product);
                shoppingCartItem.setQuantity(quantity);
                shoppingCart.add(shoppingCartItem);
            }
            return shoppingCart;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }
}
