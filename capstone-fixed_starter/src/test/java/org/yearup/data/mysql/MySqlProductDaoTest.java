package org.yearup.data.mysql;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yearup.models.Product;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;


    @BeforeEach
    public void setup()
    {
        dao = new MySqlProductDao(dataSource);
    }


    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {


        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};


        // act
        var actual = dao.getById(productId);


        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }


    @Test
    void testSearch_shouldReturnCorrectCatIDAndPriceFilterAndColor() throws SQLException {
        // Arrange
        int categoryID = 1;
        BigDecimal minPrice = BigDecimal.valueOf(80.0);
        BigDecimal maxPrice = BigDecimal.valueOf(90.0);
        String color = "Charcoal";

        Product expected = new Product();
        expected.setProductId(13);
        expected.setName("Fitness Tracker");
        expected.setPrice(new BigDecimal("89.99"));
        expected.setCategoryId(1);
        expected.setDescription("Monitor your fitness activities and achieve your health goals with this fitness tracker.");
        expected.setColor("Charcoal");
        expected.setStock(30);
        expected.setFeatured(true);
        expected.setImageUrl("fitness-tracker.jpg");

        // Act
        List<Product> actual = dao.search(categoryID, minPrice, maxPrice, color);

        // Assert
        assertEquals(1, actual.size(), "Expected number of products");

        Product actualProduct = actual.get(0);

        assertEquals(expected.getProductId(), actualProduct.getProductId(), "Product ID matches");
        assertEquals(expected.getName(), actualProduct.getName(), "Name matches");
        assertEquals(expected.getPrice(), actualProduct.getPrice(), "Price matches");
        assertEquals(expected.getCategoryId(), actualProduct.getCategoryId(), "Category ID matches");
        assertEquals(expected.getDescription(), actualProduct.getDescription(), "Description matches");
    }


}
