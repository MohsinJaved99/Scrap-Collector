package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class profile extends AppCompatActivity {
    EditText name,email,pass,contact,address;
    int id;
    String cn,em;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
        }


        name=(EditText) findViewById(R.id.profilename);
        email=(EditText) findViewById(R.id.profileemail);
        pass=(EditText) findViewById(R.id.profilepass);
        contact=(EditText) findViewById(R.id.profilecontact);
        address=(EditText) findViewById(R.id.profileaddress);
        TextView regdate=(TextView) findViewById(R.id.accregdate);


        name.clearFocus();
        email.clearFocus();
        pass.clearFocus();
        contact.clearFocus();
        address.clearFocus();

        ImageView back=(ImageView) findViewById(R.id.profileback);




        if(role.equals("admin")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile.this, Admin_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else if(role.equals("collector")) {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile.this, Collector_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(profile.this, User_Dashboard.class);
                    intent.putExtra("userid",String.valueOf(id));
                    startActivity(intent);
                    finish();
                }
            });
        }

//        name.setFocusableInTouchMode(false);
//        email.setFocusableInTouchMode(false);
//        pass.setFocusableInTouchMode(false);
//        contact.setFocusableInTouchMode(false);
//        address.setFocusableInTouchMode(false);

        Button svebtn=(Button) findViewById(R.id.profilesavebtn);
//        ImageButton ib=(ImageButton) findViewById(R.id.editprofile);



//        ib.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(name.isFocusableInTouchMode()==false) {
//                    Toast.makeText(profile.this, "Editing Enabled", Toast.LENGTH_SHORT).show();
//                    name.setFocusableInTouchMode(true);
//                    email.setFocusableInTouchMode(true);
//                    pass.setFocusableInTouchMode(true);
//                    contact.setFocusableInTouchMode(true);
//                    address.setFocusableInTouchMode(true);
//                }
//                else if(name.isFocusableInTouchMode()==true) {
//                    Toast.makeText(profile.this, "Editing Disabled", Toast.LENGTH_SHORT).show();
//                    name.setFocusableInTouchMode(false);
//                    email.setFocusableInTouchMode(false);
//                    pass.setFocusableInTouchMode(false);
//                    contact.setFocusableInTouchMode(false);
//                    address.setFocusableInTouchMode(false);
//                }
//
//            }
//        });


        TextView vhpw2=(TextView) findViewById(R.id.vhbuttonprofile);


        vhpw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vhpw2.getText().toString().equals("Show")){
                    pass.setTransformationMethod(null);
                    vhpw2.setText("Hide");
                } else{
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                    vhpw2.setText("Show");
                }
            }
        });





        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();
        SQLiteDatabase dbwrite=helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT User_Name,User_Email,User_Password,User_Address,User_Contact,User_Reg_Date FROM users WHERE User_ID = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount()>0) {

            cursor.moveToFirst();

            do {
                name.setText(cursor.getString(0));
                email.setText(cursor.getString(1));
                pass.setText(cursor.getString(2));
                address.setText(cursor.getString(3));
                contact.setText(cursor.getString(4));
                em=cursor.getString(1);
                cn=cursor.getString(4);
                regdate.setText("Account Created On " + cursor.getString(5));
            }
            while (cursor.moveToNext());
        }


        svebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (email.getText().equals("") || pass.getText().equals("") || name.getText().equals("") || address.getText().equals("") || contact.getText().equals("")) {
                        Toast.makeText(profile.this, "Please Fill All Fields.", Toast.LENGTH_SHORT).show();
                    } else {

                        Cursor cursor = db.rawQuery("SELECT User_Email,User_Contact FROM users WHERE User_ID <> ?", new String[]{String.valueOf(id)});
                        if(cursor.getCount() > 1) {
                            cursor.moveToFirst();

                            do {
                                String checkemail = cursor.getString(0);
                                String checkcontact = cursor.getString(1);


                                if (checkemail.equals(email.getText().toString())) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(profile.this)
//set icon
                                            .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                                            .setTitle("Error!")
//set message
                                            .setMessage("Email Already Registered. Please Try Again.")
//set positive button
                                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //set what would happen when positive button is clicked
                                                    email.setText(em);
                                                }
                                            })
                                            .show();
                                } else if (checkcontact.equals(contact.getText().toString())) {
                                    AlertDialog alertDialog = new AlertDialog.Builder(profile.this)
//set icon
                                            .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                                            .setTitle("Error!")
//set message
                                            .setMessage("Contact Number Already Registered. Please Try Again.")
//set positive button
                                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    //set what would happen when positive button is clicked
                                                    contact.setText(cn);
                                                }
                                            })
                                            .show();
                                } else {
                                    ContentValues value = new ContentValues();

                                    value.put("User_Name", name.getText().toString());
                                    value.put("User_Email", email.getText().toString());
                                    value.put("User_Password", pass.getText().toString());
                                    value.put("User_Contact", contact.getText().toString());
                                    value.put("User_Address", address.getText().toString());
                                    dbwrite.update("users", value, "User_ID = ?", new String[]{String.valueOf(id)});


                                    Toast.makeText(profile.this, "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                                    name.setFocusableInTouchMode(false);
                                    email.setFocusableInTouchMode(false);
                                    pass.setFocusableInTouchMode(false);
                                    contact.setFocusableInTouchMode(false);
                                    address.setFocusableInTouchMode(false);
                                }
                            } while (cursor.moveToNext());
                        }
                        else {
                            ContentValues value = new ContentValues();

                            value.put("User_Name", name.getText().toString());
                            value.put("User_Email", email.getText().toString());
                            value.put("User_Password", pass.getText().toString());
                            value.put("User_Contact", contact.getText().toString());
                            value.put("User_Address", address.getText().toString());
                            dbwrite.update("users", value, "User_ID = ?", new String[]{String.valueOf(id)});


                            Toast.makeText(profile.this, "Profile Updated Successfully.", Toast.LENGTH_LONG).show();
                            name.setFocusableInTouchMode(false);
                            email.setFocusableInTouchMode(false);
                            pass.setFocusableInTouchMode(false);
                            contact.setFocusableInTouchMode(false);
                            address.setFocusableInTouchMode(false);
                        }
                    }
                }
                catch (Exception e) {
                    Toast.makeText(profile.this, "Some Error Occurs While Updating Profile.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public void refresh(){
        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
        }
        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT User_Name,User_Email,User_Password,User_Address,User_Contact,User_Reg_Date FROM users WHERE User_ID = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount()>0) {

            cursor.moveToFirst();

            do {
                name.setText(cursor.getString(0));
                email.setText(cursor.getString(1));
                pass.setText(cursor.getString(2));
                address.setText(cursor.getString(3));
                contact.setText(cursor.getString(4));
                em=cursor.getString(1);
                cn=cursor.getString(4);
            }
            while (cursor.moveToNext());
        }
    }
 }