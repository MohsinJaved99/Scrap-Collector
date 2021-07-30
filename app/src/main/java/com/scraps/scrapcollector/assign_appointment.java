package com.scraps.scrapcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.CALL_PHONE;

public class assign_appointment extends AppCompatActivity {
    int id;
    String role;
    int idd;
    String number;
    Spinner spinner;
    String selectedcollector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_appointment);


        Intent intent= getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id=Integer.parseInt(extras.getString("userid"));
            role=extras.getString("role");
            idd=Integer.parseInt(extras.getString("idd"));
        }

        TextView ud=(TextView) findViewById(R.id.datetxt);
        TextView un=(TextView) findViewById(R.id.unametxt);
        TextView ue=(TextView) findViewById(R.id.uemailtxt);
        TextView uc=(TextView) findViewById(R.id.ucontacttxt);

        ImageView back=(ImageView) findViewById(R.id.scrapback2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(assign_appointment.this, appointments.class);
                intent.putExtra("userid", String.valueOf(id));
                intent.putExtra("role", "admin");
                startActivity(intent);
                finish();
            }
        });

        Button btn=(Button) findViewById(R.id.assign);

        spinner = (Spinner) findViewById(R.id.spinner);
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) assign_appointment.this);

        // Loading spinner data from database
        loadSpinnerData();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String label = parentView.getItemAtPosition(position).toString();


                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(label);
                while(m.find()) {
                    selectedcollector=m.group();
//                    Toast.makeText(assign_appointment.this, String.valueOf(m.group()), Toast.LENGTH_SHORT).show();
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



        DatabaseHelper helper = new DatabaseHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT a.Date,a.Time,u.User_Name,u.User_Contact,u.User_Address FROM appointments a JOIN users u ON a.User_ID=u.User_ID WHERE App_ID=?", new String[]{String.valueOf(idd)});
        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                String datetime=cursor.getString(0) + "/" + cursor.getString(1);
                String uname=cursor.getString(2);
                String uemail=cursor.getString(3);
                String ucon=cursor.getString(4);

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



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedcollector==null)  {
                    Toast.makeText(assign_appointment.this, "Please Select A Collector", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues value = new ContentValues();

                    value.put("SC_ID", String.valueOf(selectedcollector));
                    value.put("App_Status","Confirmed");
                    db.update ("appointments",value,"App_ID = ?",new String[]{String.valueOf(idd)});
                    Toast.makeText(getApplicationContext(), "Collector Assigned successfully!", Toast.LENGTH_LONG).show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(assign_appointment.this, appointments.class);
                            intent.putExtra("userid", String.valueOf(id));
                            intent.putExtra("role", "admin");
                            startActivity(intent);
                            finish();
                        }
                    }, 1000);

                }
            }
        });

    }

    private void loadSpinnerData() {
        // database handler
        DatabaseHelper helper = new DatabaseHelper(this);


        // Spinner Drop down elements
        List<String> lables = helper.getAllCollectors();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(assign_appointment.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position,
//                               long id) {
//        // On selecting a spinner item
//        String label = parent.getItemAtPosition(position).toString();
//
//        // Showing selected spinner item
//        Toast.makeText(parent.getContext(), "You selected: " + label,
//                Toast.LENGTH_LONG).show();
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> arg0) {
//        // TODO Auto-generated method stub
//
//    }
}