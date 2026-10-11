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

public class ShowtimeDAO {

    public List<Showtime> getAllShowtimes() throws SQLException {
        List<Showtime> showtimes = new ArrayList<>();

        String sql = "SELECT s.showtime_id, s.show_date, s.show_time, "
                   + "s.end_time, m.movie_id, m.title, m.ticket_price "
                   + "FROM Showtime s "
                   + "JOIN Movie m ON s.movie_id = m.movie_id "
                   + "ORDER BY s.show_date, s.show_time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                showtimes.add(new Showtime(
                        rs.getString("showtime_id"),
                        rs.getDate("show_date"),
                        rs.getTime("show_time"),
                        rs.getTime("end_time"),
                        rs.getString("movie_id"),
                        rs.getString("title"),
                        rs.getBigDecimal("ticket_price").toPlainString()
                ));
            }
        }

        return showtimes;
    }
}
