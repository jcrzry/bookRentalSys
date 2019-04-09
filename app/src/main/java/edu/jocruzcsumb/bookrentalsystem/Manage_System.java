package edu.jocruzcsumb.bookrentalsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by jcrzr on 11/28/2016.
 */

public class Manage_System extends AppCompatActivity {
    private TextView usernameLbl;
    private ListView logList;
    private Button removeList;
    private BookRental_DB db;
    private Button addBook;
    private LinearLayout bookLayout;
    private EditText bkTitle;
    private EditText bkAuth;
    private EditText bkIsbn;
    private EditText bkFee;
    private Bundle userInfo;
    private int userID;
    private Book b;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_system);
        db = new BookRental_DB(this);
        logList = (ListView)findViewById(R.id.logList);
        usernameLbl = (TextView)findViewById(R.id.usernameLbl);
        removeList = (Button)findViewById(R.id.dismissLog);
        bookLayout = (LinearLayout) findViewById(R.id.bookInfoLayout);
        addBook = (Button)findViewById(R.id.submitBook);
        bkTitle = (EditText)findViewById(R.id.title);
        bkAuth = (EditText) findViewById(R.id.author);
        bkIsbn = (EditText) findViewById(R.id.isbn);
        bkFee = (EditText) findViewById(R.id.fee);

        Intent i = getIntent();
        userInfo = i.getExtras();
        userID = userInfo.getInt("userID");
        if(userInfo.getBoolean("Admin")){
            final ArrayList<Transaction> allTrans = db.getAllTransactions();
            if(allTrans != null && !allTrans.isEmpty()){
                fillList(allTrans);
            }
            else{
                showNoLogDialogue();
            }
            removeList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logList.setVisibility(View.GONE);
                    removeList.setVisibility(View.GONE);
                    removeList.setVisibility(View.GONE);
                    askAddBookDialogue();
                }
            });

            addBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = bkTitle.getText().toString();
                    String author = bkAuth.getText().toString();
                    String isbn = bkIsbn.getText().toString();
                    double fee = Double.parseDouble(bkFee.getText().toString());
                    b = new Book(title, author,isbn,fee);
                    if(db.bookExists(title)){
                        showBookErrorDialogue();
                    }
                    else{

                        confirmDialogue(b.toString());
                    }
                }
            });
        }
        else{
            AlertDialog noAdmin = new AlertDialog.Builder(Manage_System.this).create();
            noAdmin.setTitle("LOGIN ERROR:");
            noAdmin.setMessage(("You do not have access to this page."));
            noAdmin.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
            noAdmin.show();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        ArrayList<Transaction> allTrans = db.getAllTransactions();
        if(allTrans != null && !allTrans.isEmpty()){
            fillList(allTrans);
        }
        else{
            showNoLogDialogue();
        }
    }



    public void fillList(ArrayList<Transaction> t){
        ArrayAdapter<Transaction> logAdapter = new ArrayAdapter<Transaction>(Manage_System.this,
                android.R.layout.simple_list_item_1, t);
        logList.setAdapter(logAdapter);
    }


    public void askAddBookDialogue() {
        AlertDialog noLogs = new AlertDialog.Builder(Manage_System.this).create();
        noLogs.setTitle("ADD BOOK:");
        noLogs.setMessage(("Would you like to add a book?"));
        noLogs.setButton(AlertDialog.BUTTON_POSITIVE,"YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bookLayout.setVisibility(View.VISIBLE);
                    }
                });
        noLogs.setButton(AlertDialog.BUTTON_NEGATIVE,"NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        noLogs.show();

    }

    public void showNoLogDialogue() {
        AlertDialog noLogs = new AlertDialog.Builder(Manage_System.this).create();
        noLogs.setTitle("NO LOGS FOUND");
        noLogs.setMessage(("No Logs Were Found"));
        noLogs.setButton(AlertDialog.BUTTON_NEUTRAL,"YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        noLogs.show();

    }

    public void showBookErrorDialogue() {
        AlertDialog noLogs = new AlertDialog.Builder(Manage_System.this).create();
        noLogs.setTitle("BOOK ERROR:");
        noLogs.setMessage(("Book already in database."));
        noLogs.setButton(AlertDialog.BUTTON_NEUTRAL,"YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        noLogs.show();

    }

    public void confirmDialogue(String s) {
        AlertDialog noLogs = new AlertDialog.Builder(Manage_System.this).create();
        noLogs.setTitle("Confirm:");
        noLogs.setMessage(("Is this correct: " + s));
        noLogs.setButton(AlertDialog.BUTTON_POSITIVE,"YES",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.insertBook(b);
                        GregorianCalendar g = (GregorianCalendar)Calendar.getInstance();
                        String dateTime = format(g);
                        String Type = "= Added Book";
                        Transaction t = new Transaction(Type,dateTime,userID);
                        Toast.makeText(Manage_System.this, "BOOK ADDED!", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                });
        noLogs.setButton(AlertDialog.BUTTON_NEGATIVE,"NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }

                });
        noLogs.show();

    }
    public String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}