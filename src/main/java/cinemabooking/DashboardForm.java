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

public class DashboardForm extends JFrame {

    private final User user;

    public DashboardForm(User user) {
        this.user = user;

        setTitle("Cinema Booking System - Dashboard");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        panel.add(new JLabel(
                "Welcome, " + user.getName() + " (" + user.getRole() + ")",
                SwingConstants.CENTER));

        JButton moviesButton = new JButton("View Movies");
        JButton showtimesButton = new JButton("View Showtimes");
        JButton logoutButton = new JButton("Logout");

        panel.add(moviesButton);
        panel.add(showtimesButton);

        moviesButton.addActionListener(e ->
                new MovieListForm().setVisible(true));

        showtimesButton.addActionListener(e ->
                new ShowtimeForm(user).setVisible(true));

        if ("Manager".equalsIgnoreCase(user.getRole())) {
            JButton manageInfoButton = new JButton(
                    "Manager: View System Information");
            panel.add(manageInfoButton);

            manageInfoButton.addActionListener(e ->
                    JOptionPane.showMessageDialog(this,
                            "Use the movie and showtime screens to view records."));
        }

        panel.add(logoutButton);

        logoutButton.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });

        add(panel);
    }
}