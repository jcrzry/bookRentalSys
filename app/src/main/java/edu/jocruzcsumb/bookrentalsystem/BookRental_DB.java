package edu.jocruzcsumb.bookrentalsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.StrictMode;
import android.provider.Telephony;
import android.util.Log;

import java.nio.channels.SelectableChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;


/**
 * Created by jcrzr on 11/29/2016.
 */

public class BookRental_DB{
    //logcat tag
    public static final String LOG = "BookRental_DBHelper";

    //DB version
    public static final int DATABASE_VERSION = 1;

    //DB name
    public static final String DATABASE_NAME = "Book_Rental_System";

    //Table Names
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_BOOKS = "BOOKS";
    public static final String TABLE_TRANS = "TRANSACTIONS";
    public static final String TABLE_RESERVS = "RESERVATIONS";

    //User table - column names
    public static final String USER_ID = "USER_ID"; //PK
    public static final String USERNAME = "USERNAME";
    public static final String USER_PW = "PASSWORD";
    public static final String ADMIN = "ADMIN";


    //Book table - column names
    public static final String BK_ID = "BK_ID"; //PK
    public static final String BK_TITLE = "TITLE";
    public static final String BK_AUTHOR = "AUTHOR";
    public static final String BK_FEE = "FEE";
    public static final String BK_ISBN = "ISBN";
    public static final String BK_RSVD = "RESERVED";
    public static final String Bk_AVAILABLE = "AVAILABLE";
    public static final String BK_NEXT_AVAIL_DATE = "AVAILABILITY_DATE";


    //Transaction table - column names
    public static final String TRANS_ID = "TRANS_ID"; //PK
//    public static final String USER_ID = "USER_ID"; //FK
    public static final String TRANS_TYPE = "TYPE";
    public static final String TRANS_DATETIME = "DATE_TIME";

    //Reservation table column names
    public static final String RESERV_ID = "RESERV_ID"; //PK
//    public static final String BOOK_ID = "BOOK_ID" //FK
//    public static final String TRANS_ID = "TRANS_ID" //FK
    public static final String PU_DATE_TIME = "PU_DATE_TIME";
    public static final String R_DATE_TIME = "R_DATE_TIME";
    public static final String CANCELLED = "CANCELLED";

    //create statements
    public static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USERNAME + " TEXT UNIQUE ON CONFLICT IGNORE," +
                    USER_PW + " TEXT," +
                    ADMIN + " BOOLEAN);";

    public static final String CREATE_BOOK_TABLE =
            "CREATE TABLE " + TABLE_BOOKS + " (" +
                    BK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BK_TITLE + " TEXT, " +
                    BK_ISBN + " TEXT," +
                    BK_AUTHOR + " TEXT," +
                    BK_FEE + " REAL," +
                    Bk_AVAILABLE + " BOOLEAN, " +
                    BK_NEXT_AVAIL_DATE + " TEXT, " +
                    BK_RSVD + " BOOLEAN);";

    public static final String CREATE_TRANS_TABLE =
            "CREATE TABLE " + TABLE_TRANS + " (" +
                    TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    USER_ID + " INTEGER, " +
                    TRANS_TYPE + " TEXT," +
                    TRANS_DATETIME + " TEXT, FOREIGN KEY(" +
                    USER_ID + ") REFERENCES USERS(USER_ID));";

    public static final String CREATE_RESERV_TABLE =
            "CREATE TABLE " + TABLE_RESERVS + " (" +
                    RESERV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    BK_ID + " INTEGER," +
                    TRANS_ID + " INTEGER," +
                    PU_DATE_TIME + " TEXT," +
                    R_DATE_TIME + " TEXT," +
                    CANCELLED + " BOOLEAN, FOREIGN KEY(" +
                    BK_ID + ") REFERENCES BOOKS(BK_ID), FOREIGN KEY(" +
                    TRANS_ID + ") REFERENCES TRANSACTIONS(TRANS_ID));";

    private static class BookRental_DBHelper extends SQLiteOpenHelper{
        private static BookRental_DBHelper helperInstance;

        public static synchronized BookRental_DBHelper getInstance(Context context) {
            if(helperInstance == null){
                return new BookRental_DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

            }
            else{
                return helperInstance;
            }
        }

        private BookRental_DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, null, version);
//            Log.d("createUsr", CREATE_USER_TABLE);
//            Log.d("createBK", CREATE_BOOK_TABLE);
//            Log.d("createTRANS", CREATE_TRANS_TABLE);
//            Log.d("creatRES" , CREATE_RESERV_TABLE);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_BOOK_TABLE);
            db.execSQL(CREATE_TRANS_TABLE);
            db.execSQL(CREATE_RESERV_TABLE);
            initDatabase(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVS);

            // create new tables
            onCreate(db);
        }

        //db initialization with set values
        //initialized user information
        private String[] initUsers = {"a@lice5","$brian7","!chris12!"};
        private String [] initPass = {"@csit100","123abc##","CHRIS12!!"};

        //initial books
        private String[] bkTitles = {"Hot Java","Fun Java","Algorithms for Java"};
        private String[] bkAuths = {"S. Narayanan","Y. Byun","K. Alice"};
        private String[] ISBNS = {"123-ABC-101","ABCDEF-09","CDE-77-123"};
        private double[] fees = {0.05,1.00,0.25};



        public void initDatabase(SQLiteDatabase db){

            Log.d("*******************","**************************");
            Log.d("*******************","**************************");
            Log.d("*********DATABASE****","*********INITIALIZED*****");
            Log.d("*******************","**************************");
            Log.d("*******************","**************************");
            ContentValues cv = new ContentValues();
            int count = 0;
            //insert init users
            for(String s : initUsers){
                cv.put(USERNAME,s);
                cv.put(USER_PW,initPass[count]);
                cv.put(ADMIN, 0);
                db.insert(TABLE_USERS,null,cv);
                count++;
                cv.clear();
            }
            cv.put(USERNAME, "!admin2");
            cv.put(USER_PW, "!admin2");
            cv.put(ADMIN, 1);
            db.insert(TABLE_USERS,null,cv);

            //insert init books
            count = 0;
            cv.clear();
            for(String s : bkTitles){
                cv.put(BK_TITLE,s);
                cv.put(BK_AUTHOR,bkAuths[count]);
                cv.put(BK_ISBN,ISBNS[count]);
                cv.put(BK_FEE,fees[count]);
                cv.put(Bk_AVAILABLE, "1");
                cv.put(BK_NEXT_AVAIL_DATE,"2016-07-12 17:58:00");
                db.insert(TABLE_BOOKS,null,cv);
                count++;
                cv.clear();
            }
        }

    }




    private SQLiteDatabase db;
    private static BookRental_DBHelper dbHelper;

    public BookRental_DB(Context context){

         dbHelper = BookRental_DBHelper.getInstance(context);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

//    private void closeDB() {
//        if (db != null)
//            //
//    }



    /*
      ***********************************************************************************************************************************
      ***********************************************************************************************************************************
      * **************************************************START INSERT METHODS***********************************************************
      ***********************************************************************************************************************************
      ***********************************************************************************************************************************
     */


    public long insertUser(User u){
        this.openWritableDB();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, u.getUsername());
        cv.put(USER_PW,u.getPassword());
        cv.put(ADMIN,0);
        long d = db.insert(TABLE_USERS,null, cv);
        //
        return d;
    }
    public long insertAdmin(User u){
        this.openWritableDB();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, u.getUsername());
        cv.put(USER_PW,u.getPassword());
        cv.put(ADMIN,1);
        long d = db.insert(TABLE_USERS,null, cv);
        //
        return d;
    }

    public long insertBook(Book b){
        this.openWritableDB();
        ContentValues cv = new ContentValues();
        cv.put(BK_TITLE,b.getTitle());
        cv.put(BK_AUTHOR,b.getAuthor());
        cv.put(BK_ISBN,b.getIsbn());
        cv.put(BK_FEE,b.getFee());
        cv.put(BK_RSVD, 0);
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        String dateTime = format(g);
        cv.put(BK_NEXT_AVAIL_DATE, dateTime);
        long d = db.insert(TABLE_BOOKS,null,cv);
        //
        return d;
    }

    public long insertTransaction(Transaction t){
        this.openWritableDB();
        ContentValues cv = new ContentValues();
        cv.put(USER_ID, t.getUserID());
        cv.put(TRANS_TYPE, t.getType());
        cv.put(TRANS_DATETIME, t.getDateTime());
        long d = db.insert(TABLE_TRANS,null,cv);
        //
        return d;
    }

    public long insertReservation(Reservation reservation){
        this.openWritableDB();
        ContentValues cv = new ContentValues();
        cv.put(BK_ID, reservation.getBook_id());
        cv.put(TRANS_ID, reservation.getRes_id());
        cv.put(PU_DATE_TIME, reservation.getPu_time());
        cv.put(R_DATE_TIME, reservation.getR_time());
        cv.put(CANCELLED, reservation.getCancelled());
            if(reservation.getCancelled() == 1) {
                this.updateBookAvailability(reservation.getBook_id(), 1, reservation.getR_time());
            } else {
                this.updateBookAvailability(reservation.getBook_id(), 0, reservation.getR_time());
            }
        return db.insert(TABLE_RESERVS,null,cv);
    }

    public boolean cancelReservation(int resID, int bkID){
        this.openWritableDB();
        String sql = "UPDATE " + TABLE_RESERVS +
                " SET " + CANCELLED + " = " +
                "'1' WHERE " + RESERV_ID +
                " = " + "'" + resID +"'";
        Cursor result = db.rawQuery(sql, null);
        GregorianCalendar g = (GregorianCalendar) Calendar.getInstance();
        String dateTime = format(g);
        if(result.moveToFirst()){
            boolean bUpdate = updateBookAvailability(bkID, 1, dateTime);
            if(bUpdate){
                return true;
            }
        }
        return false;
    }

    /*
      ***********************************************************************************************************************************
      ***********************************************************************************************************************************
      * **************************************************START UPDATE METHODS***********************************************************
      ***********************************************************************************************************************************
      ***********************************************************************************************************************************
     */

    public boolean updateBookAvailability(int bk_id, int availablility, String AvailDate){
        this.openWritableDB();
        String sql = "UPDATE " +
                TABLE_BOOKS + " SET "+
                Bk_AVAILABLE + " = ? ," +
                BK_NEXT_AVAIL_DATE + " = ? WHERE "+
                BK_ID + " = ?";

        String [] values = {Integer.toString(availablility), AvailDate, Integer.toString(bk_id)};
        Cursor result = db.rawQuery(sql, values);
        if(result.moveToFirst()){
            result.close();
            return true;
        }
        //
        return false;
    }


    //methods to check if certain objects already exist in db


    public boolean userExists(String username){
        this.openReadableDB();
        String query = "SELECT " + USERNAME + " FROM " + TABLE_USERS + " WHERE " + USERNAME + " =?";
        String [] searchName = {username};
        Cursor result = db.rawQuery(query,searchName);

        if(result != null && result.getCount() >0){
            result.close();
            //
            return true;
        }
        else {
            //
            return false;
        }
    }
    public boolean bookExists(String title){
        this.openReadableDB();
        String query = "SELECT " + BK_TITLE + " FROM " + TABLE_BOOKS + " WHERE " + BK_TITLE + " =?";
        String [] searchName = {title};
        Cursor result = db.rawQuery(query,searchName);

        if(result != null && result.getCount() >0){
            result.close();
            //
            return true;
        }
        else {
            //
            return false;
        }
    }
    public String getUser(int userID){
        this.openReadableDB();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USER_ID + " =?";
        String [] searchName = {Integer.toString(userID)};
        Cursor result = db.rawQuery(query,searchName);
        if(result != null && result.getCount() >0){
            result.moveToFirst();
            int idLoc = result.getColumnIndex(USER_ID);
            int usrNameLoc = result.getColumnIndex(USERNAME);
            int passLoc = result.getColumnIndex(USER_PW);
            int adminLoc = result.getColumnIndex(ADMIN);
            int user_id = result.getInt(idLoc);
            String username = result.getString(usrNameLoc);
            String password = result.getString(passLoc);
            int admin = result.getInt(adminLoc);
            result.close();
            return  username;
        }
        else{
            //
            return null;
        }
    }

    public User getUser(String username){
        this.openReadableDB();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + " =?";
        String [] searchName = {username};
        Cursor result = db.rawQuery(query,searchName);
        if(result != null && result.getCount() >0){
            result.moveToFirst();
            int idLoc = result.getColumnIndex(USER_ID);
            int usrNameLoc = result.getColumnIndex(USERNAME);
            int passLoc = result.getColumnIndex(USER_PW);
            int adminLoc = result.getColumnIndex(ADMIN);
            int user_id = result.getInt(idLoc);
            String username2 = result.getString(usrNameLoc);
            String password = result.getString(passLoc);
            int admin = result.getInt(adminLoc);
            result.close();
            //
            return new User(user_id,username2,password,admin);
        }
        else{
            //
            return null;
        }
    }
    public boolean validLogin(User u){
        this.openReadableDB();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + USERNAME + " =?";
        String [] searchName = {u.getUsername()};
        Cursor result = db.rawQuery(query, searchName);
        if(result != null && result.getCount() > 0){
            result.moveToFirst();
            int usrNameLoc = result.getColumnIndex(USERNAME);
            int passLoc = result.getColumnIndex(USER_PW);
            String username = result.getString(usrNameLoc);
            String password = result.getString(passLoc);
            result.close();
            //
            return (u.getUsername().equals(username) && u.getPassword().equals(password));
        }
        else{
            //
            return false;
        }
    }



    // gets an arraylist of all books available for rent.
    public ArrayList<Book> getAvailableBooks(String date){
        this.openReadableDB();
        String query = "SELECT * FROM " +
                TABLE_BOOKS + " WHERE " +
                Bk_AVAILABLE + " = 1 OR " +
                BK_NEXT_AVAIL_DATE + " <= Datetime('" +
                date + "')";
        Cursor result = db.rawQuery(query,null);
        if(result != null && result.getCount() >0){
            ///arraylist to hold all available book objects;
            ArrayList<Book> availableBooks = new ArrayList<>(result.getCount());
            while(result.moveToNext()){
                int id_index = result.getColumnIndex(BK_ID);
                int title_index = result.getColumnIndex(BK_TITLE);
                int isbn_index = result.getColumnIndex(BK_ISBN);
                int auth_index = result.getColumnIndex(BK_AUTHOR);
                int fee_index = result.getColumnIndex(BK_FEE);
                int avail_index = result.getColumnIndex(Bk_AVAILABLE);
                int nAD_index = result.getColumnIndex(BK_NEXT_AVAIL_DATE);

                Book aBook = new Book(result.getInt(id_index), result.getString(title_index),
                        result.getString(auth_index), result.getString(isbn_index), result.getDouble(fee_index), 1, result.getString(nAD_index));
                availableBooks.add(aBook);
            }
            result.close();
            //
            return availableBooks;
        }
        else{
            //
            return null;
        }
    }


    public int getBookID(String title){
        this.openReadableDB();
        String sql = "SELECT * FROM " +
                TABLE_BOOKS + " WHERE " +
                BK_TITLE + " = '" + title + "'";
        Cursor result = db.rawQuery(sql,null);
        if(result.moveToFirst()){
            int index = result.getColumnIndex(BK_ID);
            int ID = result.getInt(index);
            //
            return ID;
        }
        else{
            //
            return -1;
        }
    }
    public Book getBook(int ID){
        this.openReadableDB();
        String sql = "SELECT * FROM " +
                TABLE_BOOKS + " WHERE " +
                BK_ID + " = '" + ID + "'";
        Cursor result = db.rawQuery(sql,null);
        if(result.moveToFirst()){
            int id_index = result.getColumnIndex(BK_ID);
            int title_index = result.getColumnIndex(BK_TITLE);
            int isbn_index = result.getColumnIndex(BK_ISBN);
            int auth_index = result.getColumnIndex(BK_AUTHOR);
            int fee_index = result.getColumnIndex(BK_FEE);
            int avail_index = result.getColumnIndex(Bk_AVAILABLE);
            int nAD_index = result.getColumnIndex(BK_NEXT_AVAIL_DATE);
            Book aBook = new Book(result.getInt(id_index), result.getString(title_index),
                    result.getString(auth_index), result.getString(isbn_index), result.getDouble(fee_index),
                    result.getInt(avail_index), result.getString(nAD_index));
            result.close();
            //
            return aBook;
        }
        else{
            //
            return null;
        }

    }
    public String getBookTitle(int bkid){
        this.openReadableDB();
        String sql = "SELECT * FROM " +
                TABLE_BOOKS + " WHERE " +
                BK_ID + " = '" + bkid + "'";
        Cursor result = db.rawQuery(sql,null);
        if(result.moveToFirst()){
            int index = result.getColumnIndex(BK_TITLE);
            String title = result.getString(index);
            result.close();
            //
            return title;
        }
        else{
            //
            return null;
        }
    }

    public ArrayList<Transaction> getAllTransactions(){
        this.openReadableDB();
        ArrayList<Transaction> transList = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_TRANS;
        Cursor result = db.rawQuery(sql,null);
        if(result.getCount()> 0 ){
            while(result.moveToNext()){
                int idIndex = result.getColumnIndex(TRANS_ID);
                int typeIndex = result.getColumnIndex(TRANS_TYPE);
                int dateIndex = result.getColumnIndex(TRANS_DATETIME);
                int userIndex = result.getColumnIndex(USER_ID);
                String username = getUser(result.getInt(userIndex));
                transList.add(new Transaction(result.getInt(idIndex), result.getString(typeIndex), result.getString(dateIndex),result.getInt(userIndex), username));
            }
            return transList;
        }
        else{
            return null;
        }
    }


    public int getTransID(long rowID){
        this.openReadableDB();
        String sql = "SELECT "+ TRANS_ID +
                " FROM " +
                TABLE_TRANS + " WHERE ROWID = " +
                rowID;
        Cursor result = db.rawQuery(sql, null);
        if(result.moveToFirst()){
            int index = result.getColumnIndex(TRANS_ID);
            int id = result.getInt(index);
            //
            return id;
        }
        else{
            //
            return -1;
        }
    }


    public ArrayList<SimpleReservation> getReservation(int userID){
        ArrayList<SimpleReservation> res = new ArrayList<>();
        this.openReadableDB();
        String sql = "SELECT * FROM " +
                TABLE_TRANS + " t INNER JOIN " +
                TABLE_RESERVS + " res ON  t."+ TRANS_ID
                +" = res." + TRANS_ID + " WHERE t." + USER_ID +
                " = '" + userID +
                "' AND res." + CANCELLED + " = '0'";
        Log.d("getRESSQL: ", sql);
        Cursor result = db.rawQuery(sql, null);
        if(result.getCount() >0 && result != null){
            while(result.moveToNext()){
                int resIndex = result.getColumnIndex(RESERV_ID);
                int puDIndex = result.getColumnIndex(PU_DATE_TIME);
                int rDindex = result.getColumnIndex(R_DATE_TIME);
                int bookIndex = result.getColumnIndex(BK_ID);

                int res_id = result.getInt(resIndex);
                String puD = result.getString(puDIndex);
                String rD = result.getString(rDindex);
                int book_id = result.getInt(bookIndex);
                String title = getBookTitle(book_id);
                SimpleReservation r = new SimpleReservation(res_id,puD,rD,title);
                res.add(r);
            }
            for(SimpleReservation r: res){
                Log.d("CANCEL FIND_RESERVS:", r.toString());
            }

            result.close();
            //
            return res;
        }
        else{
            Log.d("CANCEL FIND RESERV:", "NULL!");
            //
            return null;
        }
    }


    public String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}
