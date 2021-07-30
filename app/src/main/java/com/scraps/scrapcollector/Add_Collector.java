package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Add_Collector extends AppCompatActivity {
    int id;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collector);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }


        Button btn=(Button) findViewById(R.id.btnuploadcollector);
        TextView vhpw=(TextView) findViewById(R.id.vhbutton33);









        EditText collectorname = (EditText)findViewById(R.id.collectorname);
        EditText collectoremail = (EditText)findViewById(R.id.collectoremail);
        EditText collectorpass = (EditText)findViewById(R.id.collectorpass);
        EditText collectorcontact = (EditText)findViewById(R.id.collectorcontact);
        EditText collectoraddress = (EditText)findViewById(R.id.collectoraddress);
        ImageView back=(ImageView) findViewById(R.id.colladdback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Add_Collector.this, Scrap_Collector.class);
                intent.putExtra("userid",String.valueOf(id));
                intent.putExtra("role","admin");
                startActivity(intent);
                finish();
            }
        });


        vhpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vhpw.getText().toString().equals("Show")){
                    collectorpass.setTransformationMethod(null);
                    vhpw.setText("Hide");
                } else{
                    collectorpass.setTransformationMethod(new PasswordTransformationMethod());
                    vhpw.setText("Show");
                }
            }
        });

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getWritableDatabase();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    if(collectoremail.getText().toString().equals("") || collectorpass.getText().toString().equals("") || collectorname.getText().toString().equals("") || collectoraddress.getText().toString().equals("") || collectorcontact.getText().toString().equals("")) {
                        Toast.makeText(Add_Collector.this, "Please Fill All Fields.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ContentValues value = new ContentValues();

                        value.put("User_Name", collectorname.getText().toString());
                        value.put("User_Email", collectoremail.getText().toString());
                        value.put("User_Password", collectorpass.getText().toString());
                        value.put("User_Contact", collectorcontact.getText().toString());
                        value.put("User_Address", collectoraddress.getText().toString());
                        value.put("User_Contact", collectorcontact.getText().toString());
                        value.put("User_Reg_Date", formattedDate);
                        value.put("Role_ID", 2);
                        db.insert("users", null, value);
                        Toast.makeText(getApplicationContext(), "Collector Added successfully!", Toast.LENGTH_LONG).show();

                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Add_Collector.this, Scrap_Collector.class);
                                intent.putExtra("userid", String.valueOf(id));
                                intent.putExtra("role", "admin");
                                startActivity(intent);

                                finish();
                            }
                        }, 2000);
                    }
//                    collectorname.setText("");
//                    collectoremail.setText("");
//                    collectorcontact.setText("");
//                    collectorpass.setText("");
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}