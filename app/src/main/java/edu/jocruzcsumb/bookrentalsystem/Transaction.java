package edu.jocruzcsumb.bookrentalsystem;

import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;

/**
 * Created by jcrzr on 11/30/2016.
 */

public class Transaction {
    private int trans_id;
    private String type;
    private String dateTime;
    private int userID;
    String username;

    public Transaction(int trans_id, String type, String dateTime, int user){
        this.trans_id = trans_id;
        this.type = type;
        this.dateTime = dateTime;
        this.userID= user;
    }
    public Transaction(String type, String dateTime, int user){
        this.trans_id = 0;
        this.type = type;
        this.dateTime = dateTime;
        this.userID = user;
    }
    public Transaction(int id, String type, String dateTime, int user, String username){
        this.trans_id = id;
        this.type = type;
        this.dateTime = dateTime;
        this.userID = user;
        this.username = username;
    }


    public int getTrans_id() {
        return trans_id;
    }

    public String getDateTime() {
        return dateTime;
    }
    public String getType(){
        return this.type;
    }
    public int getUserID(){
        return this.userID;
    }

    public String toString(){

        return ("TRANS#: " + this.trans_id + " USER#: " + userID + " USERNAME: " + username+ " TYPE: " + type + " DATE/TIME :" + dateTime);
    }


}
