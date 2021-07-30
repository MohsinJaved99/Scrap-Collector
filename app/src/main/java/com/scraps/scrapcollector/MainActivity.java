package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    int count=0;
    String useridd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView signup=(TextView) findViewById(R.id.textView5);
        TextView vhpw=(TextView) findViewById(R.id.vhbutton);
        EditText siemail=(EditText) findViewById(R.id.signinemail);
        EditText sipassword=(EditText) findViewById(R.id.signinpassword);

        Button btn1=(Button) findViewById(R.id.signinbtnn);
//        siemail.setText("a@gmail.com");
////        siemail.setText("mohsinjaved414@yahoo.com");
////        siemail.setText("mohjav031010@gmail.com");
//        sipassword.setText("123");



        SharedPreferences prefs = getSharedPreferences("myPref", MODE_PRIVATE);
        String useremail = prefs.getString("email", "n/a");//"No name defined" is the default value.
        String upww = prefs.getString("password", "n/a");

//        Toast.makeText(this, "Email : "+useremail, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "password : "+upww, Toast.LENGTH_SHORT).show();
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });



        DatabaseHelper helper=new DatabaseHelper(this);


        SQLiteDatabase dbread=helper.getReadableDatabase();



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(siemail.getText().toString().equals("") || sipassword.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please Fill All Field.", Toast.LENGTH_SHORT).show();
                }
                else {

                    String user = helper.Authenticate(MainActivity.this ,siemail.getText().toString(),sipassword.getText().toString());

                    if (user != null)
                    {

                        Cursor cursorr = dbread.rawQuery("SELECT Role_ID FROM users WHERE User_ID = ?", new String[]{user});
                        cursorr.moveToFirst();
                        if(cursorr.getColumnCount()>0) {
                            do {
                                String role = cursorr.getString(0);
                                if (role.equals("1")) {

                                    Toast.makeText(MainActivity.this, "Signing In...", Toast.LENGTH_SHORT).show();

                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                            SharedPreferences.Editor editor = prefs.edit();
                                            editor.putString("email", siemail.getText().toString());
                                            editor.putString("password", sipassword.getText().toString());
                                            editor.apply();
                                            editor.commit();
                                            Intent intent = new Intent(MainActivity.this, User_Dashboard.class);
                                            intent.putExtra("userid", user.toString());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);

                                }
                                else if(role.equals("3")) {

                                    Toast.makeText(MainActivity.this, "Signing In...", Toast.LENGTH_SHORT).show();
                                    new Timer().schedule(new TimerTask() {
                                        @Override
                                        public void run() {
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString("email", siemail.getText().toString());
                                    editor.putString("password", sipassword.getText().toString());
                                    editor.apply();
                                    editor.commit();
                                    Intent intent = new Intent(MainActivity.this, Admin_Dashboard.class);
                                    intent.putExtra("userid", user.toString());
                                    startActivity(intent);
                                            finish();
                                        }
                                    }, 2000);
                                }
                                else if(role.equals("2")) {

                                    Toast.makeText(MainActivity.this, "Signing In...", Toast.LENGTH_SHORT).show();
                                    new Timer().schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                SharedPreferences.Editor editor = prefs.edit();
                                                editor.putString("email", siemail.getText().toString());
                                                editor.putString("password", sipassword.getText().toString());
                                                editor.apply();
                                                editor.commit();
                                                Intent intent = new Intent(MainActivity.this, Collector_Dashboard.class);
                                                intent.putExtra("userid", user.toString());
                                                startActivity(intent);
                                                    finish();
                                                }
                                    }, 2000);

                                }
                            } while (cursorr.moveToNext());
                        }





                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Login Unsuccessful! Please verify your Username and Password", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });





        vhpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vhpw.getText().toString().equals("Show")){
                    sipassword.setTransformationMethod(null);
                    vhpw.setText("Hide");
                } else{
                    sipassword.setTransformationMethod(new PasswordTransformationMethod());
                    vhpw.setText("Show");
                }
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });


//        Cursor cursorr1 = dbread.rawQuery("SELECT * FROM sales", new String[]{});
//        cursorr1.moveToFirst();
//        if(cursorr1.getColumnCount()>0) {
//            Toast.makeText(this, "created", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Toast.makeText(this, "not created", Toast.LENGTH_SHORT).show();
//        }
    }




}