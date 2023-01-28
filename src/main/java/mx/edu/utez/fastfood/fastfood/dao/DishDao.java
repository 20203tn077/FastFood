package mx.edu.utez.fastfood.fastfood.dao;

import mx.edu.utez.fastfood.fastfood.model.Dish;
import mx.edu.utez.fastfood.fastfood.model.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DishDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        // TODO: 27/01/2023
        return dishes;
    }

    public boolean existsById(long id) {
        boolean exists = false;
        // TODO: 27/01/2023
        return exists;
    }
    public Dish findById(long id) {
        Dish dish = null;
        // TODO: 27/01/2023
        return dish;
    }

    public boolean create(Dish dish) {
        boolean success = false;
        // TODO: 27/01/2023
        return success;
    }

    public boolean update(Dish dish) {
        boolean success = false;
        // TODO: 27/01/2023
        return success;
    }

    public boolean delete(long id) {
        boolean success = false;
        // TODO: 27/01/2023
        return success;
    }

    public boolean attachIngredient(long dishId, Ingredient ingredient) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }

    public boolean attachIngredients(long dishId, List<Ingredient> ingredients) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }

    public boolean detachIngredient(long dishId, Ingredient ingredient) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }

    public boolean detachIngredients(long dishId, List<Ingredient> ingredients) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }

    public boolean detachAllIngredients(long dishId) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }

    public boolean syncIngredients(long dishId, List<Ingredient> ingredient) {
        boolean success = false;
        // TODO: 28/01/2023
        return success;
    }
}
