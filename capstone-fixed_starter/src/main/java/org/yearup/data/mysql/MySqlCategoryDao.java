package org.yearup.data.mysql;


import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }


    @Override
    public List<Category> getAllCategories()
    {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM categories";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet row = preparedStatement.executeQuery();
            while (row.next()) {
                Category category = mapRow(row);
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;


    }


    @Override
    public Category getById(int categoryId)
    {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);


            ResultSet row = statement.executeQuery();


            if (row.next()) {
                return mapRow(row);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    @Override
    public Category create(Category category) {
        String sql = "INSERT INTO categories(name, description) VALUES (?, ?);";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int categoryId = generatedKeys.getInt(1);

                    // Create a new Category object using the provided data
                    Category createdCategory = new Category();
                    createdCategory.setCategoryId(categoryId);
                    createdCategory.setName(category.getName());
                    createdCategory.setDescription(category.getDescription());
                    System.out.println("Category Created Successfully!");

                    return createdCategory;
                }
            }
            // Handle the case where no category was created or ID wasn't generated
            throw new RuntimeException("Failed to create category or retrieve ID.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void update(int categoryId, Category category)
    {
        // update category
        String sql = " UPDATE categories " +
                " SET name = ? " +
                "   , description = ? " +
                " WHERE category_id = ?;";
        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCategoryId());
            statement.executeUpdate();
            System.out.println("Category successfully updated!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(int categoryId)
    {
        // Delete category
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, categoryId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");


        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};


        return category;
    }
}
