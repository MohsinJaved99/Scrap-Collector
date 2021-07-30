package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class collector_appointments extends AppCompatActivity {
    ArrayList<collectorAppointment> list = new ArrayList<collectorAppointment>();
    Collector_Appointment_Adapter adapter = null;
    int id;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_appointments);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        ImageView in=(ImageView) findViewById(R.id.imageView191111);
        TextView tx=(TextView) findViewById(R.id.textView131111);
        ImageView back=(ImageView)findViewById(R.id.collectorapointmentback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(collector_appointments.this, Collector_Dashboard.class);
                intent.putExtra("userid", String.valueOf(id));
                intent.putExtra("role", "collector");
                startActivity(intent);
                finish();
            }
        });

        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT a.App_ID,a.User_ID,a.Date,a.Time,a.Entry_Date,a.App_Status,u.User_Name FROM appointments a JOIN users u ON a.User_ID=u.User_ID WHERE a.App_Status='Confirmed' and a.SC_ID= ? ORDER BY a.App_ID DESC", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
//                        Toast.makeText(appointments.this, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
            in.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                int sid = cursor.getInt(0);
                String uname = cursor.getString(6);
                int userid = cursor.getInt(1);
                String date = cursor.getString(2);
                String time = cursor.getString(3);
                String entry = cursor.getString(4);
                String status = cursor.getString(5);
                list.add(new collectorAppointment(sid, 0, userid, uname, date, time, entry, status));

            }
            while (cursor.moveToNext());
        } else {
            in.setVisibility(View.VISIBLE);
            tx.setVisibility(View.VISIBLE);
        }

        RecyclerView stdlist = (RecyclerView) findViewById(R.id.collectorAppointmentView);
        adapter = new Collector_Appointment_Adapter(list, role, id);
        stdlist.setLayoutManager(new LinearLayoutManager(collector_appointments.this));
        stdlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}