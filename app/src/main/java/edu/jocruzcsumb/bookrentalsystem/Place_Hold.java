package edu.jocruzcsumb.bookrentalsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by jcrzr on 11/28/2016.
 */

public class Place_Hold extends AppCompatActivity {
    private Button pickupDate;
    private Button r_Date;
    private Button reserveBook;
    private Button findBooks;
    private TextView dateText;
    private TextView rDateText;
    private String pu_dtString;
    private Date maxDate;
    private static String r_dtString;
    private Calendar c;
    private GregorianCalendar pu_date;
    private GregorianCalendar r_date;
    private BookRental_DB db;
    private Spinner bookSpinner;
    private boolean puSet = false;
    private boolean rSet = false;
    int Book_ID;
    private Toast promptDates;
    private String confirmMessage = "";
    private boolean isListSet = false;
    private ArrayList<Book> availableBookList;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_hold);
        promptDates = Toast.makeText(getApplicationContext(),"Please enter a pick-up and return date to view all available books",
                Toast.LENGTH_SHORT);

        db = new BookRental_DB(this);
        pickupDate = (Button)findViewById(R.id.dateButton);
        dateText = (TextView) findViewById(R.id.dateDisplay);
        r_Date = (Button)findViewById(R.id.r_dateBtn);
        rDateText = (TextView) findViewById(R.id.rDateDisplay);
        findBooks = (Button) findViewById(R.id.submit_dates);
        reserveBook = (Button) findViewById(R.id.reserve_book);
        promptDates.show();

/*
        PICKUP DATE LISTENER
        PICKUP DATE LISTENER
        ******************
        * **************
        * *****************
        * ******************
 */
        pickupDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                c = Calendar.getInstance();
                Date d = new Date();
                c.setTime(d);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DATE);
                final int year = c.get(Calendar.YEAR);
                DatePickerDialog datePicker = new DatePickerDialog(Place_Hold.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth,int selectedDay) {

                        CharSequence dateChar = (selectedMonth+1) + "-" + selectedDay + "-" + selectedYear;
                        dateText.setText(dateChar);
                        c.set(selectedYear,selectedMonth,selectedDay);

                        //assigning new calendar object
                        pu_date = new GregorianCalendar();
                        pu_date.set(Calendar.MONTH, selectedMonth);
                        pu_date.set(Calendar.DATE, selectedDay);
                        pu_date.set(Calendar.YEAR, selectedYear);

                        //c.add(selectedDay,7);
                        maxDate = c.getTime();
                        pu_dtString = dateChar.toString();
                    }
                }, year,month,day);
                datePicker.setTitle("Select Pickup Date");
                final int hour = c.get(Calendar.HOUR_OF_DAY);
                final int min = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(Place_Hold.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        timePicker.setIs24HourView(true);
                        CharSequence timeChar = selectedHour + ":" + selectedMin;
                        pu_date.set(Calendar.HOUR_OF_DAY, selectedHour);
                        pu_date.set(Calendar.MINUTE, selectedMin);
                        pu_dtString +=  (" " + timeChar.toString());
                        Date d = pu_date.getTime();
                        pu_dtString = d.toString();
                        dateText.setText(pu_dtString);
                        Log.d("pu_time: *********************************" +
                                "****************************************" +
                                "***********************************" +
                                "********************************" +
                                "*" +
                                "*" +
                                "*", d.toString());
                        Log.d("finished", "finished");
                    }
                }, hour, min, true);
                timePicker.setTitle("Select Pickup Time");
                timePicker.show();
                datePicker.show();
                puSet = true;

            }
        });

        r_Date.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Date d = new Date();
                c.setTime(d);
                final int rmonth = c.get(Calendar.MONTH);
                final int rday = c.get(Calendar.DATE);
                final int ryear = c.get(Calendar.YEAR);
                DatePickerDialog datePicker = new DatePickerDialog(Place_Hold.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth,int selectedDay) {
                        datePicker.setMaxDate(maxDate.getTime());
                        CharSequence dateChar = (selectedMonth+1) + "-" + selectedDay + "-" + selectedYear;
                        rDateText.setText(dateChar);
                        dateChar = selectedYear + "-" + selectedMonth + "-"+ selectedDay;
                        c.set(selectedYear,selectedMonth,selectedDay);
                        r_dtString = dateChar.toString();
                        //assigning new calendar object
                        r_date = new GregorianCalendar();
                        r_date.set(Calendar.MONTH, selectedMonth);
                        r_date.set(Calendar.DATE, selectedDay);
                        r_date.set(Calendar.YEAR, selectedYear);
                    }
                }, ryear,rmonth,rday);
                datePicker.setTitle("Select Return Date");

                final int rhour = c.get(Calendar.HOUR_OF_DAY);
                final int rmin = c.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(Place_Hold.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMin) {
                        timePicker.setIs24HourView(true);
                        r_date.set(Calendar.HOUR_OF_DAY, selectedHour);
                        r_date.set(Calendar.MINUTE, selectedMin);
                        Date rd = r_date.getTime();
                        r_dtString = rd.toString();
                        rDateText.setText(r_dtString);
                    }
                }, rhour, rmin, true);
                timePicker.setTitle("Select Return Time");
                timePicker.show();
                datePicker.show();
                rSet = true;
            }
        });

        findBooks.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rSet && puSet){
                    if(isWithin7Days(pu_date,r_date)){
                        Log.d("ISW7 ","this is inside find books");
                        findBooks.setVisibility(View.GONE);
                        pu_dtString = format(pu_date);
                        r_dtString = format(r_date);
                        availableBookList = getBooks(pu_dtString);
                        for(Book b: availableBookList){
                            Log.d(("BOOK b : "+ b.getID()), b.toString());
                        }
                        if(availableBookList == null && isListSet){
                            showNoBookDialog();
                        }
                        else{
                            bookSpinner = (Spinner)findViewById(R.id.book_spinner);
                            setBookSpinner(bookSpinner, availableBookList);
                            bookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Book b = (Book)adapterView.getSelectedItem();
                                    Book_ID = b.getID();
                                    String book_name = b.getTitle();
                                    Log.d("BOOK_ID:  ", Integer.toString(Book_ID));
                                    Log.d("BOOK_Title:  ", book_name);
                                    reserveBook.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    }
                    else{
                        AlertDialog dateError = new AlertDialog.Builder(Place_Hold.this).create();
                        dateError.setTitle("Rental Period Not Allowed:");
                        dateError.setMessage("You have chosen a return date out of the maximun range. You" +
                                " are only allowed to rent days for 7 days max (including hours).");
                        dateError.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                                new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i){
                                        dialogInterface.dismiss();
                                        promptDates.show();
                                    }
                                });
                        dateError.show();
                    }
                }
            }
        });

        reserveBook.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RESERVEBOOK: ", " inside reserve book");
                Intent loginLink;
                loginLink = new Intent(getApplicationContext(),Login.class);
                Bundle nextExtra = new Bundle();
                nextExtra.putString("nextActivity","Place hold");
                loginLink.putExtras(nextExtra);
                Place_Hold.this.startActivityForResult(loginLink,1);
                Log.d("RESERVEBOOK: ", " End of inside reserve book");

            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
    }


    @Override
    protected void onResume(){
        if(rSet && puSet){
            availableBookList = getBooks(pu_dtString);
            isListSet = true;
        }
        if(availableBookList != null && isListSet){
            if(!availableBookList.isEmpty()){
                setBookSpinner(bookSpinner,availableBookList);
            }
        }
        else{
            showNoBookDialog();
        }
        super.onResume();
    }


    public ArrayList<Book> getBooks(String date){
        return db.getAvailableBooks(date);
    }

    public boolean isWithin7Days(GregorianCalendar beginDate, GregorianCalendar endDate){
        GregorianCalendar maxDate = (GregorianCalendar)beginDate.clone();
        maxDate.add(Calendar.DATE,7);
        Date bfd = beginDate.getTime();
        Date maxd = maxDate.getTime();
        Date end = endDate.getTime();
        String formattedBegin = format(beginDate);
        Log.d(" ISW7 Begin Date: ", bfd.toString());
        Log.d("ISW7 FormBegin Date:", formattedBegin);
        Log.d("ISW7 max Date: ", maxd.toString());
        Log.d("ISW7 end Date: ", end.toString());
        if(maxDate.compareTo(endDate) <= 0){
            Log.d("ISW7 DateResult FALSE: ", Integer.toString(beginDate.compareTo(endDate)));
            return false;
        }
        else{
            //maxdate comes after the end date, so endDate is not more than 7 days from begindate.
            Log.d("ISW7 DateResult TRUE: ", Integer.toString(beginDate.compareTo(endDate)));
            return true;
        }
    }

    public String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                int userID = extras.getInt("userID");
                String username = extras.getString("username");
                GregorianCalendar g = (GregorianCalendar)Calendar.getInstance();
                String dateTime = format(g);
                String Type = "= Reservation";
                Transaction t = new Transaction(Type,dateTime,userID);
                long rowid = db.insertTransaction(t);
                int trans_id = db.getTransID(rowid);
                Log.d("transID value: " , Long.toString(trans_id));
                Reservation r = new Reservation(trans_id,Book_ID,pu_dtString,r_dtString);
                int resId = (int)db.insertReservation(r);
                r.setRes_id(resId);
                long d = db.insertReservation(r);
                Log.d("INSERTED RESERV ROW#: ", Long.toString(d));
                Log.d("CREATED TRANS: " , t.toString());
                Log.d("CREATED RESERV: " , r.toString());
                Book b = db.getBook(Book_ID);
                String bookTitle = db.getBookTitle(Book_ID);

                //Log.d("Book before break: ", b.toString());
                db.updateBookAvailability(Book_ID,0,r_dtString);
                //System displays the Customer username, pickup date/hour, return date/hour, book title, reservation number, and total amount owed.
                confirmMessage = ("REVERVATION CONFIRMED! \nUSERNAME: " + username + " PICKUP: " + r.getPu_time() + " RETURN: " + r.getR_time() +
                        " TITLE: " + b.getTitle() + " RESERV#: " + r.getRes_id() + " AMOUNT OWED: $" + Double.toString(calcFee(pu_date.getTime(), r_date.getTime(), b.getFee())));
                AlertDialog confirmation = new AlertDialog.Builder(Place_Hold.this).create();
                confirmation.setTitle("Reservation Confirmed:");
                confirmation.setMessage((confirmMessage));
                confirmation.setButton(AlertDialog.BUTTON_NEUTRAL,"OK",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i){
                                dialogInterface.dismiss();
                                finish();
                            }
                        });
                confirmation.show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public double calcFee(Date start,Date end, double rate){
        double fee = 0.0;
        long seconds = (end.getTime() - start.getTime())/1000;
        int hours = (int)seconds/3600;
        fee = (double)(rate * hours);
        return fee;
    }


    public void showNoBookDialog() {
        AlertDialog noBooksAlert = new AlertDialog.Builder(Place_Hold.this).create();
        noBooksAlert.setTitle("No Available Books:");
        noBooksAlert.setMessage(("There are no books available to checkout."));
        noBooksAlert.setButton(AlertDialog.BUTTON_NEUTRAL,"Exit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
//        noBooksAlert.show();
    }

    public void setBookSpinner(Spinner s, ArrayList<Book> bookList){
        if(bookList != null){
            isListSet = true;
            ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(Place_Hold.this,
                    android.R.layout.simple_spinner_item,bookList);
//            bookAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(bookAdapter);
        }
        else{
            ArrayAdapter<Book> bookAdapter = new ArrayAdapter<>(Place_Hold.this,
                    android.R.layout.simple_spinner_item,(new ArrayList<Book>()));
            s.setAdapter(bookAdapter);
        }

    }


//    public ArrayAdapter<Book> booksAvailable(){
//
//    }
}






