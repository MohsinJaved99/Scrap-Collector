package com.scraps.scrapcollector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    protected static String DB_NAME="scrapcollectorneww";
    protected static Integer DB_VERSION=4;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }




    //Role Table
    public static final String RoleTABLE="roles";

    //Role Column
    public static final String RID="Role_ID"  ;
    public static final String RName="Role_Name";


    //User Table
    public static final String UserTABLE="users";

    //User Column
    public static final String UserID="User_ID";
    public static final String UserName="User_Name";
    public static final String Email="User_Email";
    public static final String Password="User_Password";
    public static final String Address="User_Address";
    public static final String Contact="User_Contact";
    public static final String RegDate="User_Reg_Date";
    public static final String RF_ID ="Role_ID";



    public static final String ScrapTABLE="scrap";


    public static final String ScrapID="Scrap_ID";
    public static final String ScrapType="Scrap_Type";
    public static final String ScrapPrice="Scrap_Price";
    public static final String ScrapDisc="Scrap_Disc";
    public static final String ScrapImage="Scrap_Image";



    public static final String ScrapCollectorTable ="scrap_collector";

    public static final String SSID ="SC_ID";
    public static final String SCName = "SC_Name";
    public static final String SCEmail = "SC_Email";
    public static final String SCContact = "SC_Contact";
    public static final String SCPass = "SC_Password";
    public static final String SCReg = "SC_Reg_Date";


    public static final String Appointments ="appointments";

    public static final String aid ="App_ID";
    public static final String Collectorid = "SC_ID";
    public static final String Userid = "User_ID";
    public static final String AppDate = "Date";
    public static final String Time = "Time";
    public static final String EntryDate = "Entry_Date";
    public static final String status = "App_Status";




    public static final String sellingTable="sales";


    public static final String SaleID="Sale_ID";
    public static final String App_ID="App_ID";
    public static final String ScrapIDD="Scrap_ID";
    public static final String Weight="Weight";
    public static final String pPrice="Price";


    private static final String create_sale_table=
            "CREATE TABLE IF NOT EXISTS " + sellingTable + "("
                    + SaleID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + App_ID + " INTEGER, "
                    + ScrapIDD + " INTEGER, "
                    + Weight + " TEXT NOT NULL, "
                    + pPrice + " INTEGER NOT NULL, "
                    + "FOREIGN KEY (" + App_ID + ") REFERENCES " + Appointments + "(" + aid + "),"
                    + "FOREIGN KEY (" + ScrapIDD + ") REFERENCES " + ScrapTABLE + "(" + ScrapID + ")"
                    + ");";


    private static final String create_app_table=
            "CREATE TABLE IF NOT EXISTS " + Appointments + "("
                    + aid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + Collectorid + " INTEGER, "
                    + Userid + " INTEGER, "
                    + AppDate + " TEXT NOT NULL, "
                    + Time + " TEXT NOT NULL, "
                    + EntryDate + " TEXT NOT NULL, "
                    + status + " TEXT NOT NULL, "
                    + "FOREIGN KEY (" + Collectorid + ") REFERENCES " + ScrapCollectorTable + "(" + SSID + "),"
                    + "FOREIGN KEY (" + Userid + ") REFERENCES " + UserTABLE + "(" + UserID + ")"
                    + ");";


    private static final String create_sc_table=
            "CREATE TABLE IF NOT EXISTS " + ScrapCollectorTable + "("
                    + SSID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SCName + " TEXT NOT NULL, "
                    + SCEmail + " TEXT NOT NULL, "
                    + SCContact + " TEXT NOT NULL, "
                    + SCPass + " TEXT NOT NULL, "
                    + SCReg + " TEXT NOT NULL"
                    + ");";


    private static final String create_scrap_table=
            "CREATE TABLE IF NOT EXISTS " + ScrapTABLE + "("
                    + ScrapID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + ScrapType + " TEXT NOT NULL, "
                    + ScrapPrice + " INTEGER NOT NULL, "
                    + ScrapDisc + " TEXT NOT NULL, "
                    + ScrapImage + " BLOG"
                    + ");";


    private static final String create_role_table=
            "CREATE TABLE IF NOT EXISTS " + RoleTABLE + "("
            + RID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + RName + " TEXT NOT NULL"
                    + ");";



    private static final String create_user_table=
            "CREATE TABLE IF NOT EXISTS " + UserTABLE + "("
                    + UserID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + UserName + " TEXT NOT NULL, "
                    + Email + " TEXT NOT NULL UNIQUE, "
                    + Password + " TEXT NOT NULL, "
                    + Contact + " TEXT NOT NULL UNIQUE, "
                    + Address + " TEXT NOT NULL, "
                    + RegDate + " TEXT NOT NULL, "
                    + RF_ID + " INTEGER, "
                    + "FOREIGN KEY (" + RF_ID + ") REFERENCES " + RoleTABLE + "(" + RID + ")"
                    + ");";


    private static String createadmin="INSERT INTO users VALUES (null,'Admin','admin@gmail.com','admin','03441123141','-','03-Jun-2021',3)";



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("PRAGMA foreign_keys = ON");
        db.execSQL(create_role_table);
        db.execSQL(create_sc_table);
        db.execSQL(create_scrap_table);
        db.execSQL(create_user_table);
        db.execSQL(create_app_table);
        db.execSQL(create_sale_table);

        db.execSQL(createadmin);
        db.execSQL(insertRoles("User"));
        db.execSQL(insertRoles("Collector"));
        db.execSQL(insertRoles("Admin"));

    }

    private String insertRoles(String RoleName) {
        String insertquery=
                "INSERT INTO "
                + RoleTABLE + "(" + RName + ") VALUES ('" + RoleName + "');";
        return insertquery;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + RoleTABLE + "");
//        db.execSQL("DROP TABLE IF EXISTS " + UserTABLE + "");
//        db.execSQL("DROP TABLE IF EXISTS " + ScrapTABLE + "");
//        db.execSQL("DROP TABLE IF EXISTS " + ScrapCollectorTable + "");
        if (newVersion > oldVersion) {

            db.execSQL(createadmin);

        }
    }

    public String Authenticate(Context context,String Email,String Pass)
    {
        DatabaseHelper helper=new DatabaseHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT User_ID,User_Email,User_Password FROM users WHERE User_Email = ?", new String[]{Email});

        if(cursor != null && cursor.moveToFirst() && cursor.getColumnCount() > 0)
        {
            String uid= cursor.getString(0);
            String email= cursor.getString(1);
            String pass= cursor.getString(2);
            if (Pass.equals(pass)){
                return uid;
            }

        }
        return null;
    }

    public String Auth_Booking(Context context,String id,String date) {
        DatabaseHelper helper=new DatabaseHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Date FROM appointments where User_ID = ?", new String[]{id});

        if(cursor != null && cursor.moveToFirst() && cursor.getColumnCount() > 0)
        {
            String cdate= cursor.getString(0);
            if (cdate.equals(date)){
                return "True";
            }
            else {
                return "False";
            }

        }
        return "False";
    }


    public String Auth_Date(Context context,String id,String date) {
        DatabaseHelper helper=new DatabaseHelper(context);
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Date FROM appointments where App_ID = ?", new String[]{id});
        String status="";
        if(cursor != null && cursor.moveToFirst() && cursor.getColumnCount() > 0)
        {
            String cdate= cursor.getString(0);
            if (cdate.equals(date)){
                status=cdate;
            }
            else {
                status="false";
            }

        }
        return status;
    }

    public void deleteAll() {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM "+ Appointments;
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.execute();
        database.close();
    }




    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM "+ ScrapTABLE +" WHERE "+ ScrapID +" = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public void deleteCollectorData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM "+ ScrapCollectorTable +" WHERE "+ SSID +" = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public void deleteAppointmentData(int id) {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM "+ Appointments +" WHERE "+ aid +" = ?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    public List<String> getAllCollectors(){
        List<String> collectors = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT User_ID,User_Name FROM users WHERE Role_ID=2";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                do {
                    collectors.add(cursor.getString(0) + " " + cursor.getString(1));
                } while (cursor.moveToNext());
            }
        }
        else {
            collectors.add("No Collector Found");
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return collectors;
    }


    public List<String> getAllScrap(){
        List<String> scrap = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT Scrap_ID,Scrap_Type FROM scrap";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor.getCount()>0) {
            if (cursor.moveToFirst()) {
                do {
                    scrap.add(cursor.getString(0) + " " + cursor.getString(1));
                } while (cursor.moveToNext());
            }
        }
        else {
            scrap.add("No Scrap Found");
        }
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return scrap;
    }

}
