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

public class Movie {

    private String movieId;
    private String title;
    private String genre;
    private String duration;
    private String ageRestriction;
    private BigDecimal ticketPrice;

    public Movie(String movieId, String title, String genre,
                 String duration, String ageRestriction,
                 BigDecimal ticketPrice) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.ageRestriction = ageRestriction;
        this.ticketPrice = ticketPrice;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }
}