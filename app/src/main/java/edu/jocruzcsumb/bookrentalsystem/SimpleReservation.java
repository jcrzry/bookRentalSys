package edu.jocruzcsumb.bookrentalsystem;

import java.text.SimpleDateFormat;

/**
 * Created by jcrzr on 12/9/2016.
 */

public class SimpleReservation extends Reservation{
    private int res_id;
    private String pu_date;
    private String r_date;
    private String bok_title;

    public SimpleReservation(int res_id, String pu_date, String r_date, String bok_title){
        this.res_id = res_id;
        this.pu_date = pu_date;
        this.r_date = r_date;
        this.bok_title = bok_title;
    }

    public String toString(){
      return ("RESERVATION#: " +  this.res_id + " PICKUP: " + this.pu_date + " RETURN: " + this.r_date + " BOOK: " + this.bok_title);
    }

}
