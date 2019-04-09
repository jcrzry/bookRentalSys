package edu.jocruzcsumb.bookrentalsystem;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by jcrzr on 11/28/2016.
 */

public class Cancel_Hold extends AppCompatActivity {

    private BookRental_DB db;
    private TextView usernameLbl;
    int resToCancel = -1;
    private Bundle userInfo;
    private boolean emptyList;
    private Spinner cancelList;
    private Button submitCancellation;
    private int selectedResID;
    private int getSelectedBookID;
    private int userID;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cancel_hold);
        db = new BookRental_DB(this);
        usernameLbl = (TextView) findViewById(R.id.usernameLbl);
        cancelList = (Spinner)findViewById(R.id.cancel_selectionSpinner);
        submitCancellation = (Button) findViewById(R.id.cancel_res);
        Intent i = getIntent();
        userInfo = i.getExtras();
        usernameLbl.setText(userInfo.getString("username"));
        userID = userInfo.getInt("userID");
        ArrayList<SimpleReservation> r = db.getReservation(userInfo.getInt("userID"));
        if(r == null || r.isEmpty()){
            AlertDialog noResAlert = new AlertDialog.Builder(Cancel_Hold.this).create();
            noResAlert.setTitle("NO RESERVATIONS FOUND:");
            noResAlert.setMessage("No reservations found for USER: " + userInfo.get("username"));
            noResAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
        }
        else{
            populateTable(r);
        }

        submitCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialogue();
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        ArrayList<SimpleReservation> r = db.getReservation(userInfo.getInt("userID"));
        if(r == null || r.isEmpty()){
            AlertDialog noResAlert = new AlertDialog.Builder(Cancel_Hold.this).create();
            noResAlert.setTitle("NO RESERVATIONS FOUND:");
            noResAlert.setMessage("No reservations found for USER: " + userInfo.get("username"));
            noResAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            noResAlert.show();
        }
        else{
            populateTable(r);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        userInfo = getIntent().getExtras();
        usernameLbl.setText(userInfo.getString("username"));
        ArrayList<SimpleReservation> r = db.getReservation(userInfo.getInt("userID"));
        if(r == null){
            AlertDialog noResAlert = new AlertDialog.Builder(Cancel_Hold.this).create();
            noResAlert.setTitle("NO RESERVATIONS FOUND:");
            noResAlert.setMessage("No reservations found for USER: " + userInfo.get("username"));
            noResAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
        }
        else{
            populateTable(r);
        }
    }



    public void populateTable(ArrayList<SimpleReservation>reservations){
        ArrayAdapter<SimpleReservation> resAdapter = new ArrayAdapter<>(Cancel_Hold.this,
                android.R.layout.simple_spinner_item,reservations);
        cancelList.setAdapter(resAdapter);
        cancelList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Reservation r = (Reservation)adapterView.getSelectedItem();
                selectedResID = r.getRes_id();
                getSelectedBookID = r.getBook_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void showConfirmDialogue() {
        AlertDialog confirm = new AlertDialog.Builder(Cancel_Hold.this).create();
        confirm.setTitle("CANCEL RESERVATION:");
        confirm.setMessage(("Are you sure you want to cancel?"));
        confirm.setButton(AlertDialog.BUTTON_NEUTRAL,"YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean cancSuccess = db.cancelReservation(selectedResID, getSelectedBookID);
                        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
                        String dateTime = format(g);
                        Transaction t = new Transaction("=Cancel Hold",dateTime,userID);
                        db.insertTransaction(t);
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        confirm.show();

    }
    public String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }

}