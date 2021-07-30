package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class update_scrap extends AppCompatActivity {
    int id;
    String role;
    int scrapid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_scrap);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
            scrapid=extras.getInt("scrapid");
        }

        ImageView back=(ImageView) findViewById(R.id.updatescrapback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_scrap.this, scraps.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
                finish();
            }
        });


        EditText type=(EditText) findViewById(R.id.updatescraptype);
        EditText price=(EditText) findViewById(R.id.updatescrapprice);
        EditText dis=(EditText) findViewById(R.id.updatescrapdis);
        ImageView img=(ImageView) findViewById(R.id.updateimage_view);



        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();
        SQLiteDatabase dbwrite=helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT Scrap_Type,Scrap_Price,Scrap_Disc,Scrap_Image FROM scrap WHERE Scrap_ID = ?", new String[]{String.valueOf(scrapid)});
        if(cursor.getCount()>0) {

            cursor.moveToFirst();

            do {
                type.setText(cursor.getString(0));
                price.setText(cursor.getString(1));
                dis.setText(cursor.getString(2));
                byte[] Image = cursor.getBlob(3);
                Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
                img.setImageBitmap(bitmap);
            }
            while (cursor.moveToNext());
        }




        Button btn=(Button) findViewById(R.id.btnupdatescrap);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    if(type.getText().toString().equals("") || price.getText().toString().equals("") || dis.getText().toString().equals("")) {
                        Toast.makeText(update_scrap.this, "Please Fill All Fields.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContentValues value = new ContentValues();

                        value.put("Scrap_Type", type.getText().toString());
                        value.put("Scrap_Price", price.getText().toString());
                        value.put("Scrap_Disc", dis.getText().toString());
                        value.put("Scrap_Image", imageViewToByte(img));
                        db.update("scrap", value, "Scrap_ID = ?", new String[]{String.valueOf(scrapid)});
                        Toast.makeText(getApplicationContext(), "Scrap Updated successfully!", Toast.LENGTH_LONG).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(update_scrap.this, scraps.class);
                                intent.putExtra("userid", String.valueOf(id));
                                intent.putExtra("role", "admin");
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
//                    scraptype.setText("");
//                    scrapprice.setText("");
//                    scrapdis.setText("");
//                    imgview.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_24));
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}