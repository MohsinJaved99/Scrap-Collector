package com.scraps.scrapcollector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Collector_Appointment_Adapter extends RecyclerView.Adapter<Collector_Appointment_Adapter.Collector_Appointment_Holder> {

    private ArrayList<collectorAppointment> appointmentlistt=new ArrayList<collectorAppointment>();
    private String role;
    private int id;
    String number;
    String scid;

    public Collector_Appointment_Adapter(ArrayList<collectorAppointment> appointmentlistt,String role,int id) {
        this.appointmentlistt = appointmentlistt;
        this.role=role;
        this.id=id;
    }
    @NonNull
    @NotNull
    @Override
    public Collector_Appointment_Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.collectorappointmentlist,parent,false);
        return new Collector_Appointment_Adapter.Collector_Appointment_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Collector_Appointment_Adapter.Collector_Appointment_Holder holder, int position) {
        collectorAppointment mv= appointmentlistt.get(position);
        holder.date.setText("Date : "+mv.getDate());
        holder.time.setText("Time : "+mv.getTime());
        holder.uname.setText("User : "+mv.getCollector());
        holder.status.setText("Status : "+mv.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointmentlistt.size();
    }

    public class Collector_Appointment_Holder extends RecyclerView.ViewHolder{
        TextView date;
        TextView time;
        TextView uname;
        TextView status;
        ImageView edit;
        public Collector_Appointment_Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.cappointmentdate);
            time= (TextView) itemView.findViewById(R.id.cappointmentime);
            uname= (TextView) itemView.findViewById(R.id.cappointmentuser);
            status= (TextView) itemView.findViewById(R.id.cappointmentstatus);
            edit=(ImageView) itemView.findViewById(R.id.ceditappointment);



            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int elementId = appointmentlistt.get(getAdapterPosition()).getAppid();
                    int collectorid = appointmentlistt.get(getAdapterPosition()).getScid();



                        CharSequence[] items = {"View User Details", "Execute Appointment"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                        dialog.setTitle("Choose An Option");
                        dialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (item == 0) {
//                                    Toast.makeText(v.getContext(), String.valueOf(elementId), Toast.LENGTH_SHORT).show();
                                    DatabaseHelper helper = new DatabaseHelper(v.getContext());

                                    SQLiteDatabase db = helper.getReadableDatabase();


                                    Cursor cursor = db.rawQuery("SELECT a.App_ID,u.User_Name,u.User_Email,u.User_Contact,a.SC_ID FROM appointments a JOIN users u ON a.User_ID=u.User_ID WHERE a.App_ID = "+elementId, new String[]{});
                                    if (cursor.getCount() > 0) {

                                        cursor.moveToFirst();

                                        do {

                                            number = cursor.getString(3);
                                            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
//set icon
                                                    .setIcon(android.R.drawable.ic_dialog_info)
//set title
                                                    .setTitle("User Details")
//set message
                                                    .setMessage("Name : " + cursor.getString(1) + "\n\nEmail : " + cursor.getString(2) + "\n\nContact : " + cursor.getString(3))
//set positive button
                                                    .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                                            intent.setData(Uri.parse("tel:" + number));
                                                            v.getContext().startActivity(intent);
                                                            //set what would happen when positive button is clicked
                                                        }
                                                    })

                                                    .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            //set what should happen when negative button is clicked
                                                        }
                                                    })

                                                    .show();

                                        }
                                        while (cursor.moveToNext());
                                    }


                                } else {
                                    Date c = Calendar.getInstance().getTime();
                                    SimpleDateFormat df = new SimpleDateFormat("d-MMM-yyyy", Locale.getDefault());
                                    String formattedDate = df.format(c);
                                    DatabaseHelper helper=new DatabaseHelper(v.getContext());

                                    String datee = helper.Auth_Date(v.getContext(),String.valueOf(elementId),formattedDate);
//                                    Toast.makeText(v.getContext(), datee, Toast.LENGTH_SHORT).show();
                                    if(datee == "false"){

                                        Toast.makeText(v.getContext(), "Appointment booked for other date.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {

                                        Intent ii = new Intent(v.getContext(), execute_appointment.class);  // get a valid context
                                        ii.putExtra("userid", String.valueOf(id));
                                        ii.putExtra("role", "collector");
                                        ii.putExtra("idd",String.valueOf(elementId));
                                        v.getContext().startActivity(ii);

                                    }


                                }



                            }
                        });
                        dialog.show();
                    }
            });




        }




        }
}

