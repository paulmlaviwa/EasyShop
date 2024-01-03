package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
        String query = " SELECT shopping_cart.quantity, products.*, users.username " +
                " FROM shopping_cart " +
                " JOIN products " +
                " ON products.product_id = shopping_cart.product_id " +
                " JOIN users " +
                " ON shopping_cart.user_id = users.user_id" +
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

    @Override
    public ShoppingCartItem addCart(int userId, int productId, ShoppingCartItem shoppingCartItem) {
        String query1 = " SELECT quantity " +
                " FROM shopping_cart " +
                " WHERE user_id = ? " +
                "AND product_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement searchStatement = connection.prepareStatement(query1);
            searchStatement.setInt(1, userId);
            searchStatement.setInt(2, productId);
            ResultSet result = searchStatement.executeQuery();
            if(result.next() == false){
                String query2 = " INSERT INTO shopping_cart(user_id, product_id, quantity)" +
                        " VALUES (?, ?, ?); ";
                PreparedStatement addStatement = connection.prepareStatement(query2);
                addStatement.setInt(1, userId);
                addStatement.setInt(2, productId);
                addStatement.setInt(3, 1);
                addStatement.executeUpdate();
            }
            else{
                int quantity = result.getInt("quantity");
                String query3 = " UPDATE shopping_cart " +
                        " SET quantity = ? " +
                        " WHERE user_id = ? " +
                        " AND product_id = ?; ";
                PreparedStatement quantityStatement = connection.prepareStatement(query3);
                quantityStatement.setInt(1, quantity + 1);
                quantityStatement.setInt(2, userId);
                quantityStatement.setInt(3, productId);
                quantityStatement.executeUpdate();
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }
}
