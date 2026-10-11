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

public class LoginForm extends JFrame {

    private final JTextField emailField = new JTextField(20);
    private final JPasswordField passwordField = new JPasswordField(20);

    public LoginForm() {
        setTitle("Cinema Booking System - Login");
        setSize(420, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        panel.add(loginButton);
        panel.add(clearButton);

        add(panel);

        loginButton.addActionListener(e -> login());
        clearButton.addActionListener(e -> {
            emailField.setText("");
            passwordField.setText("");
        });
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Please enter email and password.");
            return;
        }

        try {
            User user = new LoginDAO().login(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(
                        this, "Login successful. Welcome " + user.getName());

                new DashboardForm(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this, "Invalid email or password.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}

