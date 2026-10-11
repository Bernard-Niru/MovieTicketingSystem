/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */

import java.sql.Date;
import java.sql.Time;

public class Showtime {

    private String showtimeId;
    private Date showDate;
    private Time showTime;
    private Time endTime;
    private String movieId;
    private String movieTitle;
    private String ticketPrice;

    public Showtime(String showtimeId, Date showDate, Time showTime,
                    Time endTime, String movieId, String movieTitle,
                    String ticketPrice) {
        this.showtimeId = showtimeId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.endTime = endTime;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.ticketPrice = ticketPrice;
    }

    public String getShowtimeId() {
        return showtimeId;
    }

    public Date getShowDate() {
        return showDate;
    }

    public Time getShowTime() {
        return showTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public String toString() {
        return showtimeId + " | " + movieTitle + " | "
                + showDate + " | " + showTime;
    }
}
    
