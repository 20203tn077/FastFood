package mx.edu.utez.fastfood.dao;

import mx.edu.utez.fastfood.exception.DaoException;
import mx.edu.utez.fastfood.model.Category;
import mx.edu.utez.fastfood.service.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private Connection con;
    private PreparedStatement pstm;
    private ResultSet rs;

    public List<Category> findAll() throws DaoException{
        List<Category> categories = new ArrayList<>();
        try {
            con = MySqlConnection.getConnection();
            pstm = con.prepareStatement("SELECT c.id, c.name FROM category c ORDER BY c.name");
            rs = pstm.executeQuery();
            while (rs.next()) categories.add(new Category(
                    rs.getLong("c.id"),
                    rs.getString("c.name")
            ));
        } catch (SQLException e) {
            throw new DaoException("Error en CategoryDao:findAll", e);
        } finally {
            MySqlConnection.closeConnection(con, pstm, rs);
        }
        return categories;
    }
}
