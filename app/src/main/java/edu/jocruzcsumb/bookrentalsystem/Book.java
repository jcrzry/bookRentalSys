package edu.jocruzcsumb.bookrentalsystem;

/**
 * Created by jcrzr on 11/28/2016.
 */

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private double fee;
    private boolean available;
    private String nextAvailDate;

    Book(String title, String author, String isbn, double fee){
        this.id = 0;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.fee = fee;
    }
    Book(int id, String title, String author, String isbn, double fee, int avail , String nxtAD){
        this.id = id;
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.fee = fee;
        if(avail == 1){
            this.available = true;
        }
        else{
            this.available = false;
        }
        this.nextAvailDate = nxtAD;
    }



    public int getID(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return this.author;
    }
    public double getFee(){
        return this.fee;
    }
    public String getIsbn(){
        return this.isbn;
    }

    public String toString(){
        String av = "";
        if(this.available){
            av = "TRUE";
        }
        else{
            av = "FALSE";
        }
        return ("( " + this.title + " " + this.author + " " +
                 " " + this.isbn + " " + Double.toString(this.fee) + "  " + "AVAIALABLE: " + av + " " + this.nextAvailDate + ")");
    }


}
