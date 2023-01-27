package mx.edu.utez.fastfood.fastfood.model.ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Ingredient> findAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        // TODO: 27/01/2023
        return ingredients;
    }

    public List<Ingredient> findByDishId(long id) {
        List<Ingredient> ingredients = new ArrayList<>();
        // TODO: 27/01/2023
        return ingredients;
    }
}
