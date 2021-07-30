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
import java.util.List;

public class scraps extends AppCompatActivity {
    ImageView imgv;
    ArrayList<Scrap> list = new ArrayList<Scrap>();
    Scrap_Adapter adapter = null;
    int id;
    public String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scraps);


        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }
//        ImageView edit=(ImageView) findViewById(R.id.editscrap);
//




        Button addnew=(Button) findViewById(R.id.addbutton);

            addnew.setVisibility(View.VISIBLE);



        list.clear();
        imgv=(ImageView) findViewById(R.id.movieimg);
        ImageView in=(ImageView) findViewById(R.id.imageView4);
        TextView tx=(TextView) findViewById(R.id.textView12);
        ImageView back=(ImageView)findViewById(R.id.scrapback);



        if(role.equals("admin")) {
            addnew.setVisibility(View.VISIBLE);

            addnew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(scraps.this, ScrapInsert.class);
                    intent.putExtra("userid",String.valueOf(id));
                    intent.putExtra("role","admin");
                    startActivity(intent);
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(scraps.this, Admin_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    intent.putExtra("role","admin");
                    startActivity(intent);
                    finish();
                }
            });
        }
        else if(role.equals("collector")) {
            addnew.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(scraps.this, Collector_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    intent.putExtra("role","collector");
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
                    Intent intent = new Intent(scraps.this, User_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    intent.putExtra("role","user");
                    startActivity(intent);
                    finish();
                }
            });
        }


        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Scrap_ID,Scrap_Type,Scrap_Price,Scrap_Disc,Scrap_Image FROM scrap ORDER BY Scrap_ID DESC", new String[]{});
        if(cursor.getCount()>0) {
            in.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                int sid = cursor.getInt(0);
                String sname = cursor.getString(1);
                String sprice = cursor.getString(2);
                String sdis = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                list.add(new Scrap(sname, sprice, sdis, image, sid));

            }
            while (cursor.moveToNext());
        }
        else {
            in.setVisibility(View.VISIBLE);
            tx.setVisibility(View.VISIBLE);
        }


        RecyclerView stdlist=(RecyclerView) findViewById(R.id.scrapView);
        adapter=new Scrap_Adapter(list,role,id);
        stdlist.setLayoutManager(new LinearLayoutManager(this));
        stdlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
        list.clear();
        ImageView in=(ImageView) findViewById(R.id.imageView4);
        TextView tx=(TextView) findViewById(R.id.textView12);
        // put your code here...
        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Scrap_ID,Scrap_Type,Scrap_Price,Scrap_Disc,Scrap_Image FROM scrap ORDER BY Scrap_ID DESC", new String[]{});
        if(cursor.getCount()>0) {
            in.setVisibility(View.GONE);
            tx.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                int sid = cursor.getInt(0);
                String sname = cursor.getString(1);
                String sprice = cursor.getString(2);
                String sdis = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                list.add(new Scrap(sname, sprice, sdis, image, sid));

            }
            while (cursor.moveToNext());
        }
        else {
            in.setVisibility(View.VISIBLE);
            tx.setVisibility(View.VISIBLE);
        }


        RecyclerView stdlist=(RecyclerView) findViewById(R.id.scrapView);
        adapter=new Scrap_Adapter(list,role,id);
        stdlist.setLayoutManager(new LinearLayoutManager(this));
        stdlist.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}