package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class execute_appointment extends AppCompatActivity {
    int id;
    String role;
    int apointmentid;
    String number;
    Spinner spinner;
    String selectedscrap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_appointment);

        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
            apointmentid=Integer.parseInt(extras.getString("idd"));
        }
        DatabaseHelper helper = new DatabaseHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();


        TextView ud=(TextView) findViewById(R.id.edate);
        TextView un=(TextView) findViewById(R.id.enametxt);
        TextView ue=(TextView) findViewById(R.id.cemailtxt);
        TextView uc=(TextView) findViewById(R.id.ccontacttxt);

        ImageView back=(ImageView)findViewById(R.id.executeback);

        Button submit=(Button) findViewById(R.id.submit);

        TextView perkg=(TextView) findViewById(R.id.perkg);
        TextView total=(TextView) findViewById(R.id.totalprice);

        spinner = (Spinner) findViewById(R.id.spinnerscrap);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) assign_appointment.this);

        // Loading spinner data from database
        loadSpinnerData();
        EditText weight=(EditText) findViewById(R.id.weight);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(execute_appointment.this, collector_appointments.class);
                intent.putExtra("userid", String.valueOf(id));
                intent.putExtra("role", "collector");
                startActivity(intent);
                finish();
            }
        });

        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Cursor cursor = db.rawQuery("SELECT Scrap_Price FROM scrap WHERE Scrap_ID=?", new String[]{String.valueOf(selectedscrap)});
                if (cursor.getCount() > 0) {

                    cursor.moveToFirst();

                    do {

                        if(weight.getText().toString().equals("")) {
                            int pricee=cursor.getInt(0);
                            perkg.setText(String.valueOf(pricee)+" Rs");
                            total.setText("0");
                        }
                        else {
                            int pricee=cursor.getInt(0);
                            int weightt=Integer.parseInt(weight.getText().toString());
                            int totalp=pricee*weightt;
                            perkg.setText(String.valueOf(pricee)+" Rs");
                            total.setText(String.valueOf(totalp));
                        }

                    }
                    while (cursor.moveToNext());

                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String label = parentView.getItemAtPosition(position).toString();


                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(label);
                while(m.find()) {
                    selectedscrap=m.group();
//                    Toast.makeText(assign_appointment.this, String.valueOf(m.group()), Toast.LENGTH_SHORT).show();
                }



                Cursor cursor = db.rawQuery("SELECT Scrap_Price FROM scrap WHERE Scrap_ID=?", new String[]{String.valueOf(selectedscrap)});
                if (cursor.getCount() > 0) {

                    cursor.moveToFirst();

                    do {

                        if(weight.getText().toString().equals("")) {
                            int pricee=cursor.getInt(0);
                            perkg.setText(String.valueOf(pricee)+" Rs");
                            total.setText("0 Rs");
                        }
                        else {
                            int pricee=cursor.getInt(0);
                            int weightt=Integer.parseInt(weight.getText().toString());
                            int totalp=pricee*weightt;
                            perkg.setText(String.valueOf(pricee)+" Rs");
                            total.setText(String.valueOf(totalp)+" Rs");
                        }

                    }
                    while (cursor.moveToNext());

                }


                // Showing selected spinner item
//                Toast.makeText(parentView.getContext(), "You selected: " + label,
//                        Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });





        Cursor cursor = db.rawQuery("SELECT a.Date,a.Time,u.User_Name,u.User_Contact,u.User_Address FROM appointments a JOIN users u ON a.User_ID=u.User_ID WHERE App_ID=?", new String[]{String.valueOf(apointmentid)});
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {


                    String datetime = cursor.getString(0) + "/" + cursor.getString(1);
                    String uname = cursor.getString(2);
                    String uemail = cursor.getString(3);
                    String ucon = cursor.getString(4);

                    un.setText(uname);
                    ue.setText(uemail);
                    uc.setText(ucon);
                    ud.setText(datetime);

//               number=cursor.getString(2);
//
//                AlertDialog alertDialog = new AlertDialog.Builder(this)
////set icon
//                        .setIcon(android.R.drawable.ic_dialog_alert)
////set title
//                        .setTitle("Collector Details")
////set message
//                        .setMessage("Name : " + cursor.getString(0) + "\nEmail : " + cursor.getString(1) + "\nContact : " + cursor.getString(2))
////set positive button
//                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
////                                Toast.makeText(assign_appointment.this, number, Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(Intent.ACTION_DIAL);
//                                intent.setData(Uri.parse("tel:"+number));
//                                startActivity(intent);
//                            }
//                        })
//
//                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                //set what should happen when negative button is clicked
//                            }
//                        })
//
//                        .show();
            }
            while (cursor.moveToNext());

        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total.getText().toString().equals("0")) {
                    Toast.makeText(execute_appointment.this, "Total price must be greater than 0.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (weight.getText().toString().equals("")) {
                        Toast.makeText(execute_appointment.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    } else {
                        ContentValues value = new ContentValues();

                        value.put("App_ID", apointmentid);
                        value.put("Scrap_ID", selectedscrap);
                        value.put("Weight", weight.getText().toString());
                        value.put("Price", total.getText().toString());
                        db.insert("sales", null, value);

                        ContentValues value1 = new ContentValues();
                        value1.put("App_Status", "Completed");
                        db.update("appointments", value1, "App_ID = ?", new String[]{String.valueOf(apointmentid)});


                        Toast.makeText(getApplicationContext(), "Appointment Execute successfully!", Toast.LENGTH_LONG).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(execute_appointment.this, collector_appointments.class);
                                intent.putExtra("userid", String.valueOf(id));
                                intent.putExtra("role", "collector");
                                startActivity(intent);
                                finish();
                            }
                        }, 2000);
                    }
                }
            }
        });

    }

    private void loadSpinnerData() {
        // database handler
        DatabaseHelper helper = new DatabaseHelper(this);


        // Spinner Drop down elements
        List<String> lables = helper.getAllScrap();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(execute_appointment.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
}