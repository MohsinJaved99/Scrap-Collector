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

import java.util.ArrayList;

public class Scrap_Collector extends AppCompatActivity {
    ArrayList<ScrapCollectors> list = new ArrayList<ScrapCollectors>();
    ScrapCollector_Adapter adapter = null;
    int id;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_collector);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        ImageView in=(ImageView) findViewById(R.id.imageView1911);
        TextView tx=(TextView) findViewById(R.id.textView1311);

        list.clear();

        Button addnew=(Button) findViewById(R.id.addbutton2);
        ImageView back=(ImageView)findViewById(R.id.scrapback2);


            addnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Scrap_Collector.this, Add_Collector.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "admin");
                    startActivity(intent);
                    finish();
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Scrap_Collector.this, Admin_Dashboard.class);
                    intent.putExtra("userid", String.valueOf(id));
                    intent.putExtra("role", "admin");
                    startActivity(intent);
                    finish();
                }
            });





        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT User_ID,User_Name,User_Email,User_Contact,User_Reg_Date FROM users WHERE Role_ID=2 ORDER BY User_ID DESC", new String[]{});
        if(cursor.getCount()>0) {
            in.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                int sid = cursor.getInt(0);
                String scname = cursor.getString(1);
                String scemail = cursor.getString(2);
                String sccontact = cursor.getString(3);
                String scregdate = cursor.getString(4);
                list.add(new ScrapCollectors(sid, scname, scemail, sccontact, scregdate));

            }
            while (cursor.moveToNext());
        }
        else {
            in.setVisibility(View.VISIBLE);
            tx.setVisibility(View.VISIBLE);
        }


        RecyclerView stdlist=(RecyclerView) findViewById(R.id.scrapcollectorView);
        adapter=new ScrapCollector_Adapter(list,role,id);
        stdlist.setLayoutManager(new LinearLayoutManager(this));
        stdlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {

        super.onResume();
        list.clear();
        ImageView in=(ImageView) findViewById(R.id.imageView1911);
        TextView tx=(TextView) findViewById(R.id.textView1311);
        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT User_ID,User_Name,User_Email,User_Contact,User_Reg_Date FROM users WHERE Role_ID=2 ORDER BY User_ID DESC", new String[]{});
        if(cursor.getCount()>0) {
            in.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                int sid = cursor.getInt(0);
                String scname = cursor.getString(1);
                String scemail = cursor.getString(2);
                String sccontact = cursor.getString(3);
                String scregdate = cursor.getString(4);
                list.add(new ScrapCollectors(sid, scname, scemail, sccontact, scregdate));

            }
            while (cursor.moveToNext());
        }
        else {
            in.setVisibility(View.VISIBLE);
            tx.setVisibility(View.VISIBLE);
        }


        RecyclerView stdlist=(RecyclerView) findViewById(R.id.scrapcollectorView);
        adapter=new ScrapCollector_Adapter(list,role,id);
        stdlist.setLayoutManager(new LinearLayoutManager(this));
        stdlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}