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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ShowtimeForm extends JFrame {

    private final User user;
    private final JTable table;
    private final List<Showtime> showtimes;

    public ShowtimeForm(User user) {
        this.user = user;

        setTitle("Movie Showtimes");
        setSize(850, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columns = {
            "Showtime ID", "Movie", "Date",
            "Start Time", "End Time", "Price"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        List<Showtime> loaded;
        try {
            loaded = new ShowtimeDAO().getAllShowtimes();

            for (Showtime s : loaded) {
                model.addRow(new Object[]{
                    s.getShowtimeId(),
                    s.getMovieTitle(),
                    s.getShowDate(),
                    s.getShowTime(),
                    s.getEndTime(),
                    s.getTicketPrice()
                });
            }
        } catch (SQLException e) {
            loaded = new java.util.ArrayList<>();
            JOptionPane.showMessageDialog(
                    this, "Could not load showtimes: " + e.getMessage());
        }

        showtimes = loaded;

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton bookButton = new JButton("Book Selected Showtime");
        add(bookButton, BorderLayout.SOUTH);

        bookButton.addActionListener(e -> openBooking());
    }

    private void openBooking() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(
                    this, "Please select a showtime.");
            return;
        }

        if (!"Customer".equalsIgnoreCase(user.getRole())
                && !"Cashier".equalsIgnoreCase(user.getRole())) {
            JOptionPane.showMessageDialog(
                    this, "Only customers and cashiers can proceed to booking.");
            return;
        }

        Showtime selected = showtimes.get(
                table.convertRowIndexToModel(row));

        new BookingForm(user, selected).setVisible(true);
    }
}

