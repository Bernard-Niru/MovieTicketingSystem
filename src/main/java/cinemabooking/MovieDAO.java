/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();

        String sql = "SELECT movie_id, title, genre, duration, "
                   + "age_restriction, ticket_price "
                   + "FROM Movie ORDER BY title";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getString("movie_id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("duration"),
                        rs.getString("age_restriction"),
                        rs.getBigDecimal("ticket_price")
                ));
            }
        }

        return movies;
    }
}