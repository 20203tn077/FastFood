package mx.edu.utez.fastfood.fastfood.dao;

import mx.edu.utez.fastfood.fastfood.exception.DaoException;
import mx.edu.utez.fastfood.fastfood.model.Ingredient;
import mx.edu.utez.fastfood.fastfood.service.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Ingredient> findAll() throws DaoException{
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT i.id, i.name FROM ingredient i ORDER BY i.name;");
            rs = pstm.executeQuery();
            while (rs.next()) ingredients.add(new Ingredient(
                    rs.getLong("i.id"),
                    rs.getString("i.name")
            ));
        } catch (SQLException e) {
            throw new DaoException("Error en IngredientDao:findAll", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return ingredients;
    }
}
