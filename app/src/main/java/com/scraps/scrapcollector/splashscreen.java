package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView i=(ImageView) findViewById(R.id.imageView2);




        Glide
                .with( this )
                .load( R.drawable.loading111 )
                .into( i );




        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        String useremail = prefs.getString("email", "n/a");//"No name defined" is the default value.
        String upww = prefs.getString("password", "n/a");




        if(useremail.equals(null) && upww.equals(null) || useremail.equals("n/a") && upww.equals("n/a")) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(splashscreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 5000);
        }
        else {
            DatabaseHelper helper=new DatabaseHelper(this);


            SQLiteDatabase dbread=helper.getReadableDatabase();



            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    String user = helper.Authenticate(splashscreen.this ,useremail.toString(),upww.toString());

                    if (user != null)
                    {

                        Cursor cursorr = dbread.rawQuery("SELECT Role_ID FROM users WHERE User_ID = ?", new String[]{user});
                        cursorr.moveToFirst();
                        if(cursorr.getColumnCount()>0) {
                            do {
                                String role = cursorr.getString(0);
                                if (role.equals("1")) {

                                            Intent intent = new Intent(splashscreen.this, User_Dashboard.class);
                                            intent.putExtra("userid", user.toString());
                                            startActivity(intent);
                                            finish();

                                }
                                else if(role.equals("3")) {


                                            Intent intent = new Intent(splashscreen.this, Admin_Dashboard.class);
                                            intent.putExtra("userid", user.toString());
                                            startActivity(intent);
                                    finish();
                                }
                                else if(role.equals("2")) {

                                            Intent intent = new Intent(splashscreen.this, Collector_Dashboard.class);
                                            intent.putExtra("userid", user.toString());
                                            startActivity(intent);
                                    finish();

                                }
                            } while (cursorr.moveToNext());
                        }





                    }
                }
            }, 5000);
        }




    }

}