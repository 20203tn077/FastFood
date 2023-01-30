package mx.edu.utez.fastfood.dao;

import mx.edu.utez.fastfood.exception.DaoException;
import mx.edu.utez.fastfood.model.Category;
import mx.edu.utez.fastfood.model.Dish;
import mx.edu.utez.fastfood.model.Ingredient;
import mx.edu.utez.fastfood.service.MySqlConnection;

import java.sql.*;
import java.util.*;

public class DishDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Dish> findAll() throws DaoException {
        List<Dish> dishes = new ArrayList<>();
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT d.id, d.name, d.description, d.price, d.registration_date, d.status, c.id, c.name, i.id, i.name FROM dish d INNER JOIN category c ON d.category_id = c.id LEFT JOIN dish_has_ingredient di ON di.dish_id = d.id LEFT JOIN ingredient i ON di.ingredient_id = i.id ORDER BY d.registration_date DESC");
            rs = pstm.executeQuery();

            Map<Long, Dish> dishMap = new HashMap<>();
            Map<Long, List<Ingredient>> ingredientListMap = new HashMap<>();

            while (rs.next()) {
                long id = rs.getLong("d.id");
                if (!ingredientListMap.containsKey(id)) {
                    dishMap.put(id, new Dish(
                            id,
                            rs.getString("d.name"),
                            rs.getString("d.description"),
                            rs.getDouble("d.price"),
                            rs.getTimestamp("d.registration_date").toLocalDateTime(),
                            rs.getBoolean("d.status"),
                            new Category(
                                    rs.getLong("c.id"),
                                    rs.getString("c.name")
                            )
                    ));
                    ingredientListMap.put(id, new ArrayList<>());
                }
                if (rs.getString("i.id") != null) ingredientListMap.get(id).add(new Ingredient(
                        rs.getLong("i.id"),
                        rs.getString("i.name")
                ));
            }
            dishMap.forEach((id, dish) -> {
                dish.setIngredients(ingredientListMap.get(id));
                dishes.add(dish);
            });
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:findAll", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return dishes;
    }

    public boolean existsById(long id) throws DaoException {
        boolean exists = false;
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT (COUNT(*) > 0) FROM dish d WHERE d.id = ?");
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) exists = rs.getBoolean(1);
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:existsById", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return exists;
    }

    public boolean isActiveById(long id) throws DaoException {
        boolean active = false;
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT d.status FROM dish d WHERE d.id = ?");
            pstm.setLong(1, id);
            rs = pstm.executeQuery();
            if (rs.next()) active = rs.getBoolean(1);
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:isActiveById", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return active;
    }

    public Dish findById(long id) throws DaoException {
        Dish dish = null;
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT d.id, d.name, d.description, d.price, d.registration_date, d.status, c.id, c.name, i.id, i.name FROM dish d INNER JOIN category c ON d.category_id = c.id LEFT JOIN dish_has_ingredient di ON di.dish_id = d.id LEFT JOIN ingredient i ON di.ingredient_id = i.id WHERE d.id = ?");
            pstm.setLong(1, id);
            rs = pstm.executeQuery();

            List<Ingredient> ingredients = new ArrayList<>();

            while (rs.next()) {
                if (rs.isFirst()) dish = new Dish(
                        rs.getLong("d.id"),
                        rs.getString("d.name"),
                        rs.getString("d.description"),
                        rs.getDouble("d.price"),
                        rs.getTimestamp("d.registration_date").toLocalDateTime(),
                        rs.getBoolean("d.status"),
                        new Category(
                                rs.getLong("c.id"),
                                rs.getString("c.name")
                        )
                );

                if (rs.getString("i.id") != null) ingredients.add(new Ingredient(
                        rs.getLong("i.id"),
                        rs.getString("i.name")
                ));

                if (rs.isLast()) dish.setIngredients(ingredients);
            }
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:findById", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return dish;
    }

    public boolean create(Dish dish) throws DaoException {
        boolean success = false;
        try {
            con = MySqlConnection.getConnection();
            con.setAutoCommit(false);

            pstm = con.prepareStatement("INSERT INTO dish (name, description, price, category_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dish.getName());
            String description = dish.getDescription();
            if (description != null && !description.isBlank()) pstm.setString(2, dish.getDescription());
            else pstm.setNull(2, Types.VARCHAR);
            pstm.setDouble(3, dish.getPrice());
            pstm.setLong(4, dish.getCategory().getId());
            success = pstm.executeUpdate() == 1;

            List<Ingredient> ingredients = dish.getIngredients();
            if (ingredients != null && !ingredients.isEmpty()) {
                rs = pstm.getGeneratedKeys();
                rs.next();
                long id = rs.getLong(1);
                pstm = con.prepareStatement("INSERT INTO dish_has_ingredient (dish_id, ingredient_id) VALUES (?, ?)");
                for (Ingredient ingredient : ingredients) {
                    pstm.setLong(1, id);
                    pstm.setLong(2, ingredient.getId());
                    pstm.addBatch();
                }
                for (int affectedRows : pstm.executeBatch()) success &= affectedRows == 1;
            }

            if (success) con.commit();
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:create", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return success;
    }

    public boolean update(Dish dish) throws DaoException {
        boolean success = false;
        try {
            con = MySqlConnection.getConnection();
            con.setAutoCommit(false);

            pstm = con.prepareStatement("UPDATE dish d SET d.name = ?, d.description = ?, d.price = ?, d.category_id = ? WHERE d.id = ?");
            pstm.setString(1, dish.getName());
            String description = dish.getDescription();
            if (description != null && !description.isBlank()) pstm.setString(2, dish.getDescription());
            else pstm.setNull(2, Types.VARCHAR);
            pstm.setDouble(3, dish.getPrice());
            pstm.setLong(4, dish.getCategory().getId());
            pstm.setLong(5, dish.getId());
            success = pstm.executeUpdate() == 1;

            Set<Long> ingredientsToAttach = new HashSet<>();
            Set<Long> ingredientsToDetach = new HashSet<>();
            pstm = con.prepareStatement("SELECT di.ingredient_id FROM dish_has_ingredient di WHERE di.dish_id = ?");
            pstm.setLong(1, dish.getId());
            rs = pstm.executeQuery();
            while (rs.next()) ingredientsToDetach.add(rs.getLong("di.ingredient_id"));
            for (Ingredient ingredient : dish.getIngredients()) {
                long id = ingredient.getId();
                if (!ingredientsToDetach.contains(id)) ingredientsToAttach.add(id);
                ingredientsToDetach.remove(id);
            }

            pstm = con.prepareStatement("INSERT INTO dish_has_ingredient (dish_id, ingredient_id) VALUES (?, ?)");
            for (long ingredientId : ingredientsToAttach) {
                pstm.setLong(1, dish.getId());
                pstm.setLong(2, ingredientId);
                pstm.addBatch();
            }
            for (int affectedRows : pstm.executeBatch()) success &= affectedRows == 1;
            pstm = con.prepareStatement("DELETE FROM dish_has_ingredient di WHERE di.dish_id = ? AND di.ingredient_id = ?");
            for (long ingredientId : ingredientsToDetach) {
                pstm.setLong(1, dish.getId());
                pstm.setLong(2, ingredientId);
                pstm.addBatch();
            }
            for (int affectedRows : pstm.executeBatch()) success &= affectedRows == 1;

            if (success) con.commit();
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:update", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return success;
    }

    public boolean delete(long id) throws DaoException {
        boolean success = false;
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("UPDATE dish d SET d.status = FALSE WHERE d.id = ?");
            pstm.setLong(1, id);
            success = pstm.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException("Error en DishDao:delete", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return success;
    }
}
