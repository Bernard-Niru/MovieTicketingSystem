/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cinemabooking;

/**
 *
 * @author LENOVO
 */
public class Seat {
     private String seatId;
    private String rowNo;
    private String category;

    public Seat(String seatId, String rowNo, String category) {
        this.seatId = seatId;
        this.rowNo = rowNo;
        this.category = category;
    }

    public String getSeatId() {
        return seatId;
    }

    public String getRowNo() {
        return rowNo;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return seatId + " - Row " + rowNo + " - " + category;
    }
}
