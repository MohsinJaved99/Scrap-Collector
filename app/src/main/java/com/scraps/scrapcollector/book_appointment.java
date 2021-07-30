package com.scraps.scrapcollector;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class book_appointment extends AppCompatActivity {
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int id;
    String role;
    String ampm="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        Button btn=(Button)findViewById(R.id.btnappointment);
        DatePicker date=(DatePicker) findViewById(R.id.date);
        TimePicker time=(TimePicker) findViewById(R.id.time);

        ImageView back=(ImageView) findViewById(R.id.bookingback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(book_appointment.this, appointments.class);
                intent.putExtra("userid", String.valueOf(id));
                intent.putExtra("role", "user");
                startActivity(intent);
                finish();
            }
        });




        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getWritableDatabase();
        SQLiteDatabase db1=helper.getReadableDatabase();


        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(book_appointment.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_info)
//set title
                        .setTitle("Appointment")
//set message
                        .setMessage("Are you sure? you want to book an appointment for "+ date.getDayOfMonth() + "-" + MONTHS[date.getMonth()] + "-" + date.getYear()+"\nAt : "+getAmPm(time.getHour(),time.getMinute()))
//set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked

                                try{

                                    String datee = helper.Auth_Booking(book_appointment.this ,String.valueOf(id),date.getDayOfMonth() + "-" + MONTHS[date.getMonth()] + "-" + date.getYear());

//                                    Toast.makeText(book_appointment.this, datee, Toast.LENGTH_SHORT).show();
                                            if(datee == "True"){
                                                Toast.makeText(book_appointment.this, "You already book an appointment for " +date.getDayOfMonth() + "-" + MONTHS[date.getMonth()] + "-" + date.getYear(), Toast.LENGTH_SHORT).show();
                                            }
                                            else {

                                                String a = null;

                                                Date c = Calendar.getInstance().getTime();
                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                                                String formattedDate = df.format(c);

                                                ContentValues value = new ContentValues();

                                                value.put("SC_ID", a);
                                                value.put("User_ID", id);
                                                value.put("Date", date.getDayOfMonth() + "-" + MONTHS[date.getMonth()] + "-" + date.getYear());
                                                value.put("Time", getAmPm(time.getHour(), time.getMinute()));
                                                value.put("Entry_Date", formattedDate);
                                                value.put("App_Status", "Pending");
                                                db.insert("appointments", null, value);
                                                Toast.makeText(getApplicationContext(), "Your Appointment request has been sent to admin for " + date.getDayOfMonth() + "-" + MONTHS[date.getMonth()] + "-" + date.getYear() + "\nAt : " + getAmPm(time.getHour(), time.getMinute()), Toast.LENGTH_LONG).show();

                                                new Timer().schedule(new TimerTask() {
                                                    @Override
                                                    public void run() {
                                                        Intent intent = new Intent(book_appointment.this, appointments.class);
                                                        intent.putExtra("userid",String.valueOf(id));
                                                        intent.putExtra("role","user");
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }, 2000);
                                            }

                                }
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        })
//set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                            }
                        })
                        .show();

            }
        });
    }

    public String getAmPm(int i,int i1) {
        String ampm1="";
        if(i>11){
            ampm1="PM";
            if(i!=12){
                i=i-12;
            }
        }
        else {
            ampm1 = "AM";
        }
        // Display the 12 hour format time in TextView
        return String.valueOf(i)+":"+String.valueOf(i1)+" "+ampm1;
    }
}