package edu.jocruzcsumb.bookrentalsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jcrzr on 11/28/2016.
 */

public class Create_Account extends AppCompatActivity{
    private EditText usrnmEdTxt;
    private EditText passEdTxt;
    private Button submit;
    private int errorCount = 0;
    private BookRental_DB db;
    private long userRow;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        db = new BookRental_DB(this);

        usrnmEdTxt = (EditText) findViewById(R.id.new_usrname);
        passEdTxt = (EditText) findViewById(R.id.new_pass);
        submit = (Button) findViewById(R.id.new_acc_submit);
        submit.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                String newUsername = usrnmEdTxt.getText().toString();
                String newPass = passEdTxt.getText().toString();
                if(validateUsrN(newUsername,newPass)){
                    User u = new User(newUsername,newPass,false);
                    insertUser(u);
                    GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
                    String dateTime = format(g);
                    Transaction newTrans = new Transaction("=Create Account", dateTime, (int)userRow);
                    db.insertTransaction(newTrans);
                    Toast.makeText(getApplicationContext(),"Account Created!",Toast.LENGTH_LONG).show();
                    Thread thread = new Thread();
                    thread.start();
                    try{
                        Thread.sleep(Toast.LENGTH_LONG);
                        finish();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Incorrect username/password format! Try again.",Toast.LENGTH_LONG).show();
                    errorCount++;
                    if (errorCount == 2) {
                        AlertDialog errorAlert = new AlertDialog.Builder(Create_Account.this).create();
                        errorAlert.setTitle("Alert:");
                        errorAlert.setMessage("Too many attempts! Will navigate to main menu.");
                        errorAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                });
                        errorAlert.show();
                    }
                }
            }
        });

    }


    public boolean validateUsrN(String username, String password){
        //pattern ^(?=.*[0-9])(?=.*[a-zA-Z]{3,})(?=.*[!@#$%^&*])(?=\S+$).{5,}$
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z]{3,})(?=.*[!@#$%^&*])(?=\\S+$).{5,}$");
        Matcher usrnMatch = p.matcher(username);
        Matcher passMatch = p.matcher(password);
        if(usrnMatch.matches() && passMatch.matches()){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean insertUser(User u){
        if(!db.userExists(u.getUsername())){
            userRow = db.insertUser(u);
            return true;
        }
        else{
            Log.d("InsertError:", "Could not insert user into database");
            Toast.makeText(getApplicationContext(),"Error: User already Exists",Toast.LENGTH_LONG).show();
            return false;
        }

    }

    public String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}
