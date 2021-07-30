package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Collector_Dashboard extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_dashboard);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
        }

        TextView uname=(TextView) findViewById(R.id.collectorusername);
        TextView date=(TextView) findViewById(R.id.collectordate);

        CardView scraplist=(CardView) findViewById(R.id.cscraplist);
        CardView profile=(CardView) findViewById(R.id.cprofile);
        CardView appointment=(CardView) findViewById(R.id.capointment);
        CardView reports=(CardView) findViewById(R.id.creports);

        ImageView logout=(ImageView) findViewById(R.id.clogout);

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Collector_Dashboard.this, Collector_Report.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","collector");
                startActivity(intent);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Collector_Dashboard.this, collector_appointments.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","collector");
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Collector_Dashboard.this, profile.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","collector");
                startActivity(intent);
            }
        });


        scraplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Collector_Dashboard.this, scraps.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","collector");
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Collector_Dashboard.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                        .setTitle("Sign Out")
//set message
                        .setMessage("Are you sure to sign out?")
//set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
//                prefs.edit().remove("email").commit();
//                prefs.edit().remove("password").commit();
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(Collector_Dashboard.this, MainActivity.class);
                                startActivity(intent);
                                finish();
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


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        date.setText(formattedDate);



        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT User_Name FROM users WHERE User_ID="+id, new String[]{});
        if(cursor.getCount()>0) {

            cursor.moveToFirst();

            do {
                uname.setText(cursor.getString(0) +" (Collector)");
            }
            while (cursor.moveToNext());
        }
    }
}