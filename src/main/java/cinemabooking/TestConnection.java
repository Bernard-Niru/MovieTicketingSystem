/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */
import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try (Connection con = DBConnection.getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println("SUCCESS: Connected to the 'CinemaBooking' database successfully!");
            } else {
                System.out.println("FAILURE: Connection object is null or closed.");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: Could not connect to the database.");
            ex.printStackTrace();
        }
    }
}
