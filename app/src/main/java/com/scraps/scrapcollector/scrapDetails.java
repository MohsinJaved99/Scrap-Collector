package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class scrapDetails extends AppCompatActivity {
    int id;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_details);
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        ImageView back=(ImageView)findViewById(R.id.scrapdeatilback);

        ImageView in=(ImageView)findViewById(R.id.imageView3011);
        TextView it=(TextView) findViewById(R.id.textView3511);

        TextView scraptypetxt=(TextView)findViewById(R.id.collectedscraptype);
        TextView scrapweighttxt=(TextView)findViewById(R.id.collectedscrap);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(scrapDetails.this, Admin_Dashboard.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
                finish();
            }
        });

        StringBuilder buildertype=new StringBuilder();
        StringBuilder builderweight=new StringBuilder();
        // put your code here...
        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT sc.Scrap_Type,SUM(s.Weight) AS total FROM sales s LEFT JOIN scrap sc ON s.Scrap_ID=sc.Scrap_ID GROUP BY sc.Scrap_ID", new String[]{});
//        Toast.makeText(this, String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
        if(cursor.getCount()>0) {
            in.setVisibility(View.GONE);
            it.setVisibility(View.GONE);
            cursor.moveToFirst();

            do {
                String scraptype = cursor.getString(0);
                int price = cursor.getInt(1);

                buildertype.append(scraptype +" : \n\n");
                builderweight.append(price +" Kg\n\n");
            }
            while (cursor.moveToNext());

        }
        else {
            in.setVisibility(View.VISIBLE);
            it.setVisibility(View.VISIBLE);
            buildertype.append("");
            builderweight.append("");
        }

        scraptypetxt.setText(buildertype);
        scrapweighttxt.setText(builderweight);

    }
}