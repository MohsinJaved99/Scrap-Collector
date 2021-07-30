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

public class Admin_Dashboard extends AppCompatActivity {
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
        }

        TextView uname=(TextView) findViewById(R.id.adminname);
        TextView date=(TextView) findViewById(R.id.admindate);

        CardView scrap=(CardView) findViewById(R.id.adminscraplist);
        CardView profile=(CardView) findViewById(R.id.adminprofile);
        CardView reports=(CardView) findViewById(R.id.adminreport);
        CardView scrapcollector=(CardView) findViewById(R.id.scrapcollector);
        CardView appointment=(CardView) findViewById(R.id.adminapointment);
        CardView collectedscrap=(CardView) findViewById(R.id.admincollectedscarp);

        collectedscrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, scrapDetails.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, Collector_Report.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, appointments.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });

        ImageView logout=(ImageView) findViewById(R.id.logoutadmin);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(Admin_Dashboard.this)
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
                                Intent intent = new Intent(Admin_Dashboard.this, MainActivity.class);
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

        scrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, scraps.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, profile.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
            }
        });

        scrapcollector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin_Dashboard.this, Scrap_Collector.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
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
                uname.setText(cursor.getString(0) + " (Admin)");
            }
            while (cursor.moveToNext());
        }

    }
}