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

public class SeatDAO {

    public List<Seat> getAvailableSeats(String showtimeId)
            throws SQLException {

        List<Seat> seats = new ArrayList<>();

        String sql = "SELECT s.seat_id, s.row_no, s.category "
                   + "FROM Seat s "
                   + "WHERE NOT EXISTS ("
                   + "SELECT 1 FROM Booking_Seat bs "
                   + "WHERE bs.seat_id = s.seat_id "
                   + "AND bs.showtime_id = ? "
                   + "AND bs.status = 'Booked') "
                   + "ORDER BY s.row_no, s.seat_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, showtimeId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    seats.add(new Seat(
                            rs.getString("seat_id"),
                            rs.getString("row_no"),
                            rs.getString("category")
                    ));
                }
            }
        }

        return seats;
    }
}

