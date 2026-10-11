/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */
import java.math.BigDecimal;
import java.sql.*;

public class BookingDAO {

    public String createBooking(String customerId, String cashierUserId,
                                Showtime showtime, Seat seat,
                                String paymentMethod)
            throws SQLException {

        String bookingId;
        String reference;
        BigDecimal amount = new BigDecimal(showtime.getTicketPrice());

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                // Confirm that the customer exists.
                String customerSql =
                        "SELECT customer_id FROM Customer WHERE customer_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(customerSql)) {
                    ps.setString(1, customerId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Customer ID does not exist.");
                        }
                    }
                }

                // Confirm that the cashier exists.
                String cashierSql =
                        "SELECT user_id FROM Cashier WHERE user_id = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(cashierSql)) {
                    ps.setString(1, cashierUserId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Cashier user ID does not exist.");
                        }
                    }
                }

                // Confirm that the seat is still available.
                String seatSql =
                        "SELECT seat_id FROM Booking_Seat "
                      + "WHERE seat_id = ? AND showtime_id = ? "
                      + "AND status = 'Booked' FOR UPDATE";

                try (PreparedStatement ps =
                             con.prepareStatement(seatSql)) {
                    ps.setString(1, seat.getSeatId());
                    ps.setString(2, showtime.getShowtimeId());

                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            throw new SQLException(
                                    "This seat is already booked.");
                        }
                    }
                }

                bookingId = nextId(con, "Booking", "booking_id", "B");
                reference = "REF-" + bookingId.substring(1);

                String bookingSql =
                        "INSERT INTO Booking "
                      + "(booking_id, total_amount, booking_time, "
                      + "booking_reference, customer_id, user_id) "
                      + "VALUES (?, ?, NOW(), ?, ?, ?)";

                try (PreparedStatement ps =
                             con.prepareStatement(bookingSql)) {
                    ps.setString(1, bookingId);
                    ps.setBigDecimal(2, amount);
                    ps.setString(3, reference);
                    ps.setString(4, customerId);
                    ps.setString(5, cashierUserId);
                    ps.executeUpdate();
                }

                String seatBookingSql =
                        "INSERT INTO Booking_Seat "
                      + "(bookingSeat_id, status, booking_id, "
                      + "seat_id, showtime_id) "
                      + "VALUES (?, 'Booked', ?, ?, ?)";

                try (PreparedStatement ps =
                             con.prepareStatement(seatBookingSql)) {
                    ps.setString(1,
                            nextId(con, "Booking_Seat", "bookingSeat_id", "BS"));
                    ps.setString(2, bookingId);
                    ps.setString(3, seat.getSeatId());
                    ps.setString(4, showtime.getShowtimeId());
                    ps.executeUpdate();
                }

                new PaymentDAO().savePayment(
                        con, bookingId, paymentMethod, amount);

                con.commit();
                return bookingId;

            } catch (SQLException | RuntimeException ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    private String nextId(Connection con, String table,
                          String column, String prefix)
            throws SQLException {

        String sql = "SELECT MAX(CAST(SUBSTRING(" + column
                   + ", " + (prefix.length() + 1)
                   + ") AS UNSIGNED)) FROM " + table;

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int nextNumber = 1;

            if (rs.next()) {
                nextNumber = rs.getInt(1) + 1;
            }

            return prefix + String.format("%03d", nextNumber);
        }
    }
}

