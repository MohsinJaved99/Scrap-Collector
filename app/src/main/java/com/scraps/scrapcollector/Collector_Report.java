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

public class Collector_Report extends AppCompatActivity {
    ArrayList<collectorReportClass> list = new ArrayList<collectorReportClass>();
    collector_Report_Adapter adapter = null;
    int id;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_report);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        ImageView in=(ImageView) findViewById(R.id.imageView42);
        TextView tx=(TextView) findViewById(R.id.textView122);
        TextView heading=(TextView) findViewById(R.id.textViewrptt);

        ImageView back=(ImageView)findViewById(R.id.collectorreportback);



        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        if(role.equals("collector")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Collector_Report.this, Collector_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "collector");
                    startActivity(intent);
                    finish();
                }
            });
            Cursor cursor = db.rawQuery("SELECT s.Sale_ID,a.Date,u.User_Name,u.User_Contact,sc.Scrap_Type,s.Weight,s.Price FROM sales s JOIN appointments a ON s.App_ID=a.App_ID JOIN users u ON u.User_ID=a.User_ID JOIN scrap sc ON sc.Scrap_ID=s.Scrap_ID WHERE a.SC_ID = ? ORDER BY s.Sale_ID DESC", new String[]{String.valueOf(id)});
            if (cursor.getCount() > 0) {
                in.setVisibility(View.GONE);
                tx.setVisibility(View.GONE);
                cursor.moveToFirst();

                do {
                    int saleid = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String uname = cursor.getString(2);
                    String ucontact = cursor.getString(3);
                    String scraptype = cursor.getString(4);
                    String weight = cursor.getString(5);
                    int price = cursor.getInt(6);
                    list.add(new collectorReportClass(saleid, date, uname, ucontact, scraptype, weight, price));

                }
                while (cursor.moveToNext());
            } else {
                in.setVisibility(View.VISIBLE);
                tx.setVisibility(View.VISIBLE);
            }


            RecyclerView stdlist = (RecyclerView) findViewById(R.id.collectorReportView);
            adapter = new collector_Report_Adapter(list, role, id);
            stdlist.setLayoutManager(new LinearLayoutManager(this));
            stdlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else if(role.equals("user")) {

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Collector_Report.this, User_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "user");
                    startActivity(intent);
                    finish();
                }
            });
            Cursor cursor = db.rawQuery("SELECT s.Sale_ID,a.Date,u.User_Name,u.User_Contact,sc.Scrap_Type,s.Weight,s.Price FROM sales s JOIN appointments a ON s.App_ID=a.App_ID JOIN users u ON u.User_ID=a.SC_ID JOIN scrap sc ON sc.Scrap_ID=s.Scrap_ID WHERE a.User_ID = ? ORDER BY s.Sale_ID DESC", new String[]{String.valueOf(id)});
            if (cursor.getCount() > 0) {
                in.setVisibility(View.GONE);
                tx.setVisibility(View.GONE);
                cursor.moveToFirst();

                do {
                    int saleid = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String uname = cursor.getString(2);
                    String ucontact = cursor.getString(3);
                    String scraptype = cursor.getString(4);
                    String weight = cursor.getString(5);
                    int price = cursor.getInt(6);
                    list.add(new collectorReportClass(saleid, date, uname, ucontact, scraptype, weight, price));

                }
                while (cursor.moveToNext());
            } else {
                in.setVisibility(View.VISIBLE);
                tx.setVisibility(View.VISIBLE);
            }


            RecyclerView stdlist = (RecyclerView) findViewById(R.id.collectorReportView);
            adapter = new collector_Report_Adapter(list, role, id);
            stdlist.setLayoutManager(new LinearLayoutManager(this));
            stdlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else if(role.equals("admin")) {

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Collector_Report.this, Admin_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "admin");
                    startActivity(intent);
                    finish();
                }
            });
            Cursor cursor = db.rawQuery("SELECT s.Sale_ID,a.Date,u.User_Name,u.User_Contact,sc.Scrap_Type,s.Weight,s.Price FROM sales s JOIN appointments a ON s.App_ID=a.App_ID JOIN users u ON u.User_ID=a.SC_ID JOIN scrap sc ON sc.Scrap_ID=s.Scrap_ID ORDER BY s.Sale_ID DESC", new String[]{});
            if (cursor.getCount() > 0) {
                in.setVisibility(View.GONE);
                tx.setVisibility(View.GONE);
                cursor.moveToFirst();

                do {
                    int saleid = cursor.getInt(0);
                    String date = cursor.getString(1);
                    String uname = cursor.getString(2);
                    String ucontact = cursor.getString(3);
                    String scraptype = cursor.getString(4);
                    String weight = cursor.getString(5);
                    int price = cursor.getInt(6);
                    list.add(new collectorReportClass(saleid, date, uname, ucontact, scraptype, weight, price));

                }
                while (cursor.moveToNext());
            } else {
                in.setVisibility(View.VISIBLE);
                tx.setVisibility(View.VISIBLE);
            }


            RecyclerView stdlist = (RecyclerView) findViewById(R.id.collectorReportView);
            adapter = new collector_Report_Adapter(list, role, id);
            stdlist.setLayoutManager(new LinearLayoutManager(this));
            stdlist.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}