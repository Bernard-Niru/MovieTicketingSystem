/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BookingForm extends JFrame {

    private final User user;
    private final Showtime showtime;

    private final JTextField customerIdField = new JTextField(15);
    private final JComboBox<Seat> seatCombo = new JComboBox<>();
    private final JComboBox<String> paymentCombo =
            new JComboBox<>(new String[]{"Cash", "Card", "Online"});

    public BookingForm(User user, Showtime showtime) {
        this.user = user;
        this.showtime = showtime;

        setTitle("Book Movie Ticket");
        setSize(450, 320);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Movie:"));
        panel.add(new JLabel(showtime.getMovieTitle()));

        panel.add(new JLabel("Showtime:"));
        panel.add(new JLabel(
                showtime.getShowDate() + " " + showtime.getShowTime()));

        panel.add(new JLabel("Ticket Price:"));
        panel.add(new JLabel("Rs. " + showtime.getTicketPrice()));

        panel.add(new JLabel("Customer ID:"));
        panel.add(customerIdField);

        panel.add(new JLabel("Available Seat:"));
        panel.add(seatCombo);

        panel.add(new JLabel("Payment Method:"));
        panel.add(paymentCombo);

        JButton bookButton = new JButton("Confirm Booking");
        JButton refreshButton = new JButton("Refresh Seats");

        panel.add(bookButton);
        panel.add(refreshButton);

        add(panel);

        if ("Customer".equalsIgnoreCase(user.getRole())) {
            try {
                customerIdField.setText(findCustomerId(user.getUserId()));
                customerIdField.setEditable(false);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this, "Could not find customer: " + ex.getMessage());
            }
        }

        refreshSeats();

        refreshButton.addActionListener(e -> refreshSeats());
        bookButton.addActionListener(e -> saveBooking());
    }

    private String findCustomerId(String userId) throws SQLException {
        String sql = "SELECT customer_id FROM Customer WHERE user_id = ?";

        try (java.sql.Connection con = DBConnection.getConnection();
             java.sql.PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("customer_id");
                }
            }
        }

        throw new SQLException(
                "No customer profile is linked to this user account.");
    }

    private void refreshSeats() {
        try {
            seatCombo.removeAllItems();

            List<Seat> seats =
                    new SeatDAO().getAvailableSeats(showtime.getShowtimeId());

            for (Seat seat : seats) {
                seatCombo.addItem(seat);
            }

            if (seats.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this, "No seats are currently available.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Could not load seats: " + e.getMessage());
        }
    }

    private void saveBooking() {
        String customerId = customerIdField.getText().trim();
        Seat seat = (Seat) seatCombo.getSelectedItem();
        String paymentMethod = (String) paymentCombo.getSelectedItem();

        if (customerId.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter the customer ID.");
            return;
        }

        if (seat == null) {
            JOptionPane.showMessageDialog(
                    this, "Please select an available seat.");
            return;
        }

        String cashierUserId;

        if ("Cashier".equalsIgnoreCase(user.getRole())) {
            cashierUserId = user.getUserId();
        } else {
            JOptionPane.showMessageDialog(
                    this, "Your database requires a cashier for each booking. "
                    + "Please ask a cashier to complete this booking.");
            return;
        }

        int answer = JOptionPane.showConfirmDialog(
                this,
                "Confirm booking for seat " + seat.getSeatId()
                + "?\nAmount: Rs. " + showtime.getTicketPrice()
                + "\nPayment method: " + paymentMethod,
                "Confirm Booking",
                JOptionPane.YES_NO_OPTION
        );

        if (answer != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            String bookingId = new BookingDAO().createBooking(
                    customerId,
                    cashierUserId,
                    showtime,
                    seat,
                    paymentMethod
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Booking successful!"
                    + "\nBooking ID: " + bookingId
                    + "\nSeat: " + seat.getSeatId()
                    + "\nAmount: Rs. " + showtime.getTicketPrice()
                    + "\nPayment: " + paymentMethod
            );

            refreshSeats();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this, "Booking failed: " + ex.getMessage());
        }
    }
}

