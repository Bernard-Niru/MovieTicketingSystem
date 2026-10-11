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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDAO {

    public void savePayment(Connection con, String bookingId,
                            String paymentMethod, BigDecimal amount)
            throws SQLException {

        String sql = "INSERT INTO Payment "
                   + "(payment_id, payment_method, payment_time, "
                   + "amount, status, booking_id) "
                   + "VALUES (?, ?, NOW(), ?, ?, ?)";

        String paymentId = generatePaymentId(con);

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, paymentId);
            ps.setString(2, paymentMethod);
            ps.setBigDecimal(3, amount);
            ps.setString(4, "Completed");
            ps.setString(5, bookingId);

            ps.executeUpdate();
        }
    }

    private String generatePaymentId(Connection con)
            throws SQLException {

        String sql = "SELECT MAX(CAST(SUBSTRING(payment_id, 2) "
                   + "AS UNSIGNED)) FROM Payment";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int nextNumber = 1;

            if (rs.next()) {
                nextNumber = rs.getInt(1) + 1;
            }

            return String.format("P%03d", nextNumber);
        }
    }
}
