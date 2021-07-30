package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class appointments extends AppCompatActivity {
    ArrayList<Appoint> list = new ArrayList<Appoint>();
    Appointment_Adapter adapter = null;
    int id;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }


        Button addnew=(Button) findViewById(R.id.addAppointmentbtn);
        ImageView back=(ImageView)findViewById(R.id.aapointmentback);

        ImageView in=(ImageView) findViewById(R.id.imageView19111);
        TextView tx=(TextView) findViewById(R.id.textView13111);


        if(role.equals("user")) {
            addnew.setVisibility(View.VISIBLE);
            addnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appointments.this, book_appointment.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "user");
                    startActivity(intent);
                    finish();
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appointments.this, User_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "user");
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            addnew.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(appointments.this, Admin_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "admin");
                    startActivity(intent);
                    finish();
                }
            });
        }
        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Button pending=(Button) findViewById(R.id.pendingAppointment);
        Button confirmed=(Button) findViewById(R.id.confirmedAppointment);

        confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(role.equals("admin")) {
                    list.clear();
                    Cursor cursor = db.rawQuery("SELECT a.App_ID,a.User_ID,a.Date,a.Time,a.Entry_Date,a.App_Status,u.User_Name FROM appointments a JOIN users u ON a.SC_ID=u.User_ID WHERE a.App_Status='Confirmed' ORDER BY a.SC_ID DESC", new String[]{});
                    if (cursor.getCount() > 0) {
//                        Toast.makeText(appointments.this, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
                        in.setVisibility(View.GONE);
                        tx.setVisibility(View.GONE);
                        cursor.moveToFirst();

                        do {
                            int sid = cursor.getInt(0);
                            String scname = cursor.getString(6);
                            int userid = cursor.getInt(1);
                            String date = cursor.getString(2);
                            String time = cursor.getString(3);
                            String entry = cursor.getString(4);
                            String status = cursor.getString(5);
                            list.add(new Appoint(sid, 0, userid, scname, date, time, entry, status));

                        }
                        while (cursor.moveToNext());
                    } else {
                        in.setVisibility(View.VISIBLE);
                        tx.setVisibility(View.VISIBLE);
                    }

                    RecyclerView stdlist = (RecyclerView) findViewById(R.id.appointmentView);
                    adapter = new Appointment_Adapter(list, role, id);
                    stdlist.setLayoutManager(new LinearLayoutManager(appointments.this));
                    stdlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    list.clear();
                    Cursor cursor = db.rawQuery("SELECT a.App_ID,a.User_ID,a.Date,a.Time,a.Entry_Date,a.App_Status,u.User_Name FROM appointments a JOIN users u ON a.SC_ID=u.User_ID WHERE a.User_ID = ? AND a.App_Status='Confirmed' ORDER BY a.SC_ID DESC ", new String[]{String.valueOf(id)});
                    if (cursor.getCount() > 0) {
                        in.setVisibility(View.GONE);
                        tx.setVisibility(View.GONE);
                        cursor.moveToFirst();

                        do {
                            int sid = cursor.getInt(0);
                            String scname = cursor.getString(6);
                            int userid = cursor.getInt(1);
                            String date = cursor.getString(2);
                            String time = cursor.getString(3);
                            String entry = cursor.getString(4);
                            String status = cursor.getString(5);
                            list.add(new Appoint(sid, 0, userid, scname, date, time, entry, status));

                        }
                        while (cursor.moveToNext());
                    } else {
                        in.setVisibility(View.VISIBLE);
                        tx.setVisibility(View.VISIBLE);
                    }

                    RecyclerView stdlist = (RecyclerView) findViewById(R.id.appointmentView);
                    adapter = new Appointment_Adapter(list, role, id);
                    stdlist.setLayoutManager(new LinearLayoutManager(appointments.this));
                    stdlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }
        });


        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(appointments.this, "FAfaf", Toast.LENGTH_SHORT).show();
                if(role.equals("admin")) {
                    list.clear();
                    Cursor cursor = db.rawQuery("SELECT App_ID,User_ID,Date,Time,Entry_Date,App_Status FROM appointments WHERE App_Status='Pending' ORDER BY SC_ID DESC ", new String[]{});
                    if (cursor.getCount() > 0) {
//                        Toast.makeText(appointments.this, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
                        in.setVisibility(View.GONE);
                        tx.setVisibility(View.GONE);
                        cursor.moveToFirst();

                        do {
                            int sid = cursor.getInt(0);
                            String scname = "Not Assigned";
                            int userid = cursor.getInt(1);
                            String date = cursor.getString(2);
                            String time = cursor.getString(3);
                            String entry = cursor.getString(4);
                            String status = cursor.getString(5);
                            list.add(new Appoint(sid, 0, userid, scname, date, time, entry, status));

                        }
                        while (cursor.moveToNext());
                    } else {
                        in.setVisibility(View.VISIBLE);
                        tx.setVisibility(View.VISIBLE);
                    }

                    RecyclerView stdlist = (RecyclerView) findViewById(R.id.appointmentView);
                    adapter = new Appointment_Adapter(list, role, id);
                    stdlist.setLayoutManager(new LinearLayoutManager(appointments.this));
                    stdlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    list.clear();
                    Cursor cursor = db.rawQuery("SELECT App_ID,User_ID,Date,Time,Entry_Date,App_Status FROM appointments WHERE User_ID = ? AND App_Status='Pending' ORDER BY SC_ID DESC ", new String[]{String.valueOf(id)});
                    if (cursor.getCount() > 0) {
                        in.setVisibility(View.GONE);
                        tx.setVisibility(View.GONE);
                        cursor.moveToFirst();

                        do {
                            int sid = cursor.getInt(0);
                            String scname = "Not Assigned";
                            int userid = cursor.getInt(1);
                            String date = cursor.getString(2);
                            String time = cursor.getString(3);
                            String entry = cursor.getString(4);
                            String status = cursor.getString(5);
                            list.add(new Appoint(sid, 0, userid, scname, date, time, entry, status));

                        }
                        while (cursor.moveToNext());
                    } else {
                        in.setVisibility(View.VISIBLE);
                        tx.setVisibility(View.VISIBLE);
                    }

                    RecyclerView stdlist = (RecyclerView) findViewById(R.id.appointmentView);
                    adapter = new Appointment_Adapter(list, role, id);
                    stdlist.setLayoutManager(new LinearLayoutManager(appointments.this));
                    stdlist.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });



//        DatabaseHelper helper=new DatabaseHelper(this);
//
//        SQLiteDatabase db=helper.getReadableDatabase();
//        Cursor c=db.rawQuery("SELECT SC_ID FROM appointments WHERE User_ID = ?", new String[]{String.valueOf(id)});
//
//        if(c.getCount()>0) {
//            c.moveToFirst();
//            do {
////                int aa=c.getInt(0);
//                String ssid = String.valueOf(c.getString(0));
//
//                if(ssid.equals("null")) {
//                    Cursor cursor = db.rawQuery("SELECT App_ID,User_ID,Date,Time,Entry_Date,App_Status FROM appointments WHERE User_ID = ? ORDER BY SC_ID DESC ", new String[]{String.valueOf(id)});
//                    if (cursor.getCount() > 0) {
//                        in.setVisibility(View.GONE);
//                        tx.setVisibility(View.GONE);
//                        cursor.moveToFirst();
//
//                        do {
//                            int sid = cursor.getInt(0);
//                            String scname = "Not Assigned";
//                            int userid = cursor.getInt(1);
//                            String date = cursor.getString(2);
//                            String time = cursor.getString(3);
//                            String entry = cursor.getString(4);
//                            String status = cursor.getString(5);
//                            list.add(new Appoint(sid,0, userid, scname, date, time, entry, status));
//
//                        }
//                        while (cursor.moveToNext());
//                    } else {
//                        in.setVisibility(View.VISIBLE);
//                        tx.setVisibility(View.VISIBLE);
//                    }
//                }
//                else {
//                    Cursor cursor = db.rawQuery("SELECT a.App_ID,a.SC_ID,s.SC_Name,a.User_ID,a.Date,Time,a.Entry_Date,a.App_Status FROM appointments a JOIN scrap_collector s ON a.SC_ID=s.SC_ID WHERE User_ID = ? ORDER BY SC_ID DESC", new String[]{String.valueOf(id)});
//                    if (cursor.getCount() > 0) {
//                        in.setVisibility(View.GONE);
//                        tx.setVisibility(View.GONE);
//                        cursor.moveToFirst();
//
//                        do {
//                            int sid = cursor.getInt(0);
//                            int scid = cursor.getInt(1);
//                            String scname = cursor.getString(2);
//                            int userid = cursor.getInt(3);
//                            String date = cursor.getString(4);
//                            String time = cursor.getString(5);
//                            String entry = cursor.getString(6);
//                            String status = cursor.getString(7);
//                            list.add(new Appoint(sid, scid , userid, scname, date, time, entry, status));
//
//                        }
//                        while (cursor.moveToNext());
//                    } else {
//                        in.setVisibility(View.VISIBLE);
//                        tx.setVisibility(View.VISIBLE);
//                    }
//                }
//            }
//            while (c.moveToNext());
//        }



    }
}