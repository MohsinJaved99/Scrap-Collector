package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ScrapInsert extends AppCompatActivity {
    Button btnbrowse, btnupload;
    ImageView imgview;
    Uri FilePathUri;
    int Image_Request_Code = 7;
    EditText scraptype,scrapprice,scrapdis;
    int id;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap_insert);


        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }

        btnbrowse = (Button)findViewById(R.id.btnbrowes);
        btnupload = (Button)findViewById(R.id.btnupload);


        scraptype = (EditText)findViewById(R.id.scraptype);
        scrapprice = (EditText)findViewById(R.id.scrapprice);
        scrapdis = (EditText)findViewById(R.id.scrapdis);


        ImageView back=(ImageView)findViewById(R.id.sinsertback);


            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ScrapInsert.this, scraps.class);
                    intent.putExtra("userid",String.valueOf(id));
                    intent.putExtra("role","admin");
                    startActivity(intent);
                    finish();
                }
            });



        imgview = (ImageView)findViewById(R.id.image_view);
        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
            }
        });

        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getWritableDatabase();

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    if(scraptype.getText().toString().equals("") || scrapprice.getText().toString().equals("") || scrapdis.getText().toString().equals("")) {
                        Toast.makeText(ScrapInsert.this, "Please Fill All Fields.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContentValues value = new ContentValues();

                        value.put("Scrap_Type", scraptype.getText().toString());
                        value.put("Scrap_Price", scrapprice.getText().toString());
                        value.put("Scrap_Disc", scrapdis.getText().toString());
                        value.put("Scrap_Image", imageViewToByte(imgview));
                        db.insert("scrap", null, value);
                        Toast.makeText(getApplicationContext(), "Scrap Added successfully!", Toast.LENGTH_LONG).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(ScrapInsert.this, scraps.class);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}