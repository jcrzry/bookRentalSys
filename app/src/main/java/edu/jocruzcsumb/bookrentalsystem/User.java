package edu.jocruzcsumb.bookrentalsystem;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jcrzr on 11/29/2016.
 */

public class User {
    private int user_id;
    private String username;
    private String password;
    private boolean admin;
    private SQLiteDatabase db;

    public User(int user_id, String username, String password, boolean admin){
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public User(String username, String password, boolean admin){
        this.username = username;
        this.password = password;
        this.admin = admin;
    }
    public User(int user_id, String username, String password, int admin){
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        if(admin ==0 ){
            this.admin = false;
        }
        else{
            this.admin = true;
        }

    }
    public User(int id, String username, String password){
        this.user_id = id;
        this.username = username;
        this.password = password;
    }


    //Mutator methods
    public void setUser_id(int newID){
        this.user_id = newID;
    }
    public void setUsername(String newUsrname){
        this.username = newUsrname;
    }
    public void setPassword(String newPW){
        this.password = newPW;
    }

    //getter methods
    public int getUser_id(){
        return user_id;
    }
    public String getUsername(){
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return admin;
    }
}
