package mx.edu.utez.fastfood.fastfood.dao;

import mx.edu.utez.fastfood.fastfood.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        // TODO: 27/01/2023
        return categories;
    }

    public Category findByDishId(long id) {
        Category category = null;
        // TODO: 27/01/2023
        return category;
    }
}
