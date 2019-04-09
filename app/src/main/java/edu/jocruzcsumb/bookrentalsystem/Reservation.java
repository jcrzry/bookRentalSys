package edu.jocruzcsumb.bookrentalsystem;

/**
 * Created by jcrzr on 11/30/2016.
 */

public class Reservation {
    private int res_id;
    private int trans_id;
    private int book_id;
    private String pu_time;
    private String r_time;
    private int cancelled;
    private String bookTitle;
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public Reservation(){
    }

    public Reservation(int res_id, int trans_id, int book_id, String pu_time, String r_time,
                       int cancelled){
        this.res_id = res_id;
        this.trans_id = trans_id;
        this.book_id = book_id;
        this.pu_time = pu_time;
        this.r_time = r_time;
        this.cancelled = FALSE;
    }
    public Reservation(int trans_id, int book_id, String pu_time, String r_time){
        this.trans_id = trans_id;
        this.book_id = book_id;
        this.pu_time = pu_time;
        this.r_time = r_time;
        this.cancelled = FALSE;
    }

    public Reservation(int res_id, String pu_time, String r_time, String bookTitle){
        this.res_id = res_id;
        this.pu_time = pu_time;
        this.r_time = r_time;
        this.cancelled = FALSE;
        this.bookTitle = bookTitle;
    }

    public void setRes_id(int res_id){
        this.res_id = res_id;
    }
    public void setTrans_id(int trans_id){
        this.trans_id = trans_id;
    }
    public void setBook_id(int book_id){
        this.book_id = book_id;
    }

    public void setPu_time(String pu_time) {
        this.pu_time = pu_time;
    }

    public void setR_time(String r_time) {
        this.r_time = r_time;
    }

    public void setCancelled(int cancelled) {
        this.cancelled = cancelled;
    }

    //getters

    public int getRes_id() {
        return res_id;
    }

    public int getTrans_id() {
        return trans_id;
    }
    public int getBook_id(){
        return this.book_id;
    }
    public String getR_time() {
        return r_time;
    }
    public int getCancelled(){
        return this.cancelled;
    }
    public String getPu_time() {
        return pu_time;
    }
    public String getBookTitle(){
        return this.bookTitle;
    }

    @Override
    public String toString() {
        String can = "";
        if(this.cancelled == 1){
            can = "CANCELLED";
        }
        else{
            can = "ACTIVE";
        }
        return ("RESERVATION#: " + res_id + " TRANS#: " + trans_id + " BOOK#: " + book_id + " PICKUP: " + pu_time + " RETURN: " +
        r_time + " STATUS: " + can);
    }
}
