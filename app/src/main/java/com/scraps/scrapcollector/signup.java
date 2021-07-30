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
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class signup extends AppCompatActivity {


    EditText name,email,pass,contact,address;
    Button signupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView vhpw1=(TextView) findViewById(R.id.vhbutton2);



        name=(EditText) findViewById(R.id.signupname);
        email=(EditText) findViewById(R.id.signupemial);
        pass=(EditText) findViewById(R.id.signuppassword);
        contact=(EditText) findViewById(R.id.signupcontact);
        address=(EditText) findViewById(R.id.signupaddress);


        signupbtn =(Button) findViewById(R.id.signupbutton);


        DatabaseHelper helper=new DatabaseHelper(this);

        SQLiteDatabase db=helper.getWritableDatabase();
        SQLiteDatabase dbread=helper.getReadableDatabase();
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

if(email.getText().toString().equals("") || pass.getText().toString().equals("") || name.getText().toString().equals("") || address.getText().toString().equals("") || contact.getText().toString().equals("")) {
    Toast.makeText(signup.this, "Please Fill All Fields.", Toast.LENGTH_SHORT).show();
}
else {
    Cursor cursor = dbread.rawQuery("SELECT User_Email,User_Contact FROM users", new String[]{});
    if (cursor.getCount() > 0) {

        cursor.moveToFirst();

        do {
            String checkemail = cursor.getString(0);
            String checkcontact = cursor.getString(1);


            if (checkemail.equals(email.getText().toString())) {
                AlertDialog alertDialog = new AlertDialog.Builder(signup.this)
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
                                email.setText("");
                            }
                        })
                        .show();
            } else if (checkcontact.equals(contact.getText().toString())) {
                AlertDialog alertDialog = new AlertDialog.Builder(signup.this)
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
                                contact.setText("");
                            }
                        })
                        .show();
            } else {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);

                ContentValues value = new ContentValues();

                value.put("User_Name", name.getText().toString());
                value.put("User_Email", email.getText().toString());
                value.put("User_Password", pass.getText().toString());
                value.put("User_Contact", contact.getText().toString());
                value.put("User_Address", address.getText().toString());
                value.put("User_Reg_Date", formattedDate);
                value.put("Role_ID", 1);
                db.insert("users", null, value);

                AlertDialog alertDialog = new AlertDialog.Builder(signup.this)
//set icon
                        .setIcon(android.R.drawable.ic_dialog_info)
//set title
                        .setTitle("Success!")
//set message
                        .setMessage("Account Created Successfully.")
//set positive button
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Intent intent = new Intent(signup.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

                name.setText("");
                email.setText("");
                pass.setText("");
                contact.setText("");
                address.setText("");
            }


        }
        while (cursor.moveToNext());
    } else {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        ContentValues value = new ContentValues();

        value.put("User_Name", name.getText().toString());
        value.put("User_Email", email.getText().toString());
        value.put("User_Password", pass.getText().toString());
        value.put("User_Contact", contact.getText().toString());
        value.put("User_Address", address.getText().toString());
        value.put("User_Reg_Date", formattedDate);
        value.put("Role_ID", 3);
        db.insert("users", null, value);

        AlertDialog alertDialog = new AlertDialog.Builder(signup.this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_info)
//set title
                .setTitle("Success!")
//set message
                .setMessage("Account Created Successfully.")
//set positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        Intent intent = new Intent(signup.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .show();

        name.setText("");
        email.setText("");
        pass.setText("");
        contact.setText("");
        address.setText("");
    }

}
                }
                catch (Exception e){
                    AlertDialog alertDialog = new AlertDialog.Builder(signup.this)
//set icon
                            .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                            .setTitle("Error!")
//set message
                            .setMessage("Some Error Occurs While Creating Account. Please Try Again.")
//set positive button
                            .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //set what would happen when positive button is clicked
                                    email.setText("");
                                    contact.setText("");
                                }
                            })
                            .show();
                }
            }
        });




        vhpw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vhpw1.getText().toString().equals("Show")){
                    pass.setTransformationMethod(null);
                    vhpw1.setText("Hide");
                } else{
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                    vhpw1.setText("Show");
                }
            }
        });

        TextView signin=(TextView) findViewById(R.id.sin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for go back to activity...
//                finish();
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}