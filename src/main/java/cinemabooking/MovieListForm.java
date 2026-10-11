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

public class MovieListForm extends JFrame {

    public MovieListForm() {
        setTitle("Available Movies");
        setSize(750, 400);
        setLocationRelativeTo(null);

        String[] columns = {
            "Movie ID", "Title", "Genre", "Duration",
            "Age Restriction", "Ticket Price"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);

        try {
            List<Movie> movies = new MovieDAO().getAllMovies();

            for (Movie movie : movies) {
                model.addRow(new Object[]{
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDuration(),
                    movie.getAgeRestriction(),
                    movie.getTicketPrice()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this, "Could not load movies: " + e.getMessage());
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
