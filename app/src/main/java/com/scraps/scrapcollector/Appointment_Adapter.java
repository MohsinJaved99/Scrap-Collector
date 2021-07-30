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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Appointment_Adapter extends RecyclerView.Adapter<Appointment_Adapter.Appointment_Holder> {

    private Scrap_Adapter.OnItemClickListener mListener;
    private ArrayList<Appoint> appointmentlistt=new ArrayList<Appoint>();
    private String role;
    private int id;
    String number;
    String scid;

    public Appointment_Adapter(ArrayList<Appoint> appointmentlistt,String role,int id) {
        this.appointmentlistt = appointmentlistt;
        this.role=role;
        this.id=id;
    }

    @NonNull
    @NotNull
    @Override
    public Appointment_Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.appointmentlist,parent,false);
        return new Appointment_Adapter.Appointment_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Appointment_Adapter.Appointment_Holder holder, int position) {
        Appoint mv= appointmentlistt.get(position);
        holder.date.setText("Date : "+mv.getDate());
        holder.time.setText("Time : "+mv.getTime());
        holder.cname.setText("Collector : "+mv.getCollector());
        holder.status.setText("Status : "+mv.getStatus());

        if(mv.getStatus().equals("Confirmed") && role.equals("admin")) {
            holder.edit.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return appointmentlistt.size();
    }

    public class Appointment_Holder extends RecyclerView.ViewHolder{
        TextView date;
        TextView time;
        TextView cname;
        TextView status;
        ImageView edit;
        public Appointment_Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            date= (TextView) itemView.findViewById(R.id.appointmentdate);
            time= (TextView) itemView.findViewById(R.id.appointmentime);
            cname= (TextView) itemView.findViewById(R.id.appointmentcollector);
            status= (TextView) itemView.findViewById(R.id.appointmentstatus);
            edit=(ImageView) itemView.findViewById(R.id.editappointment);




            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int elementId = appointmentlistt.get(getAdapterPosition()).getAppid();
                    int collectorid = appointmentlistt.get(getAdapterPosition()).getScid();


                    if (role.equals("admin")) {

                        CharSequence[] items = {"Assign Collector", "Delete Appointment"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                        dialog.setTitle("Choose An Option");
                        dialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                    if (item == 0) {

                                        // update

                                Intent i = new Intent(v.getContext(), assign_appointment.class);
                                i.putExtra("userid",String.valueOf(id));
                                i.putExtra("role","admin");
                                i.putExtra("idd",String.valueOf(elementId));
                                v.getContext().startActivity(i);
                                    } else {
                                        // delete
                                        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(v.getContext());
                                        dialogDelete.setTitle("Warning!!");
                                        dialogDelete.setMessage("Are you sure you want to delete your appointment?");
                                        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                try {
                                                    DatabaseHelper helper = new DatabaseHelper(v.getContext());
                                                    helper.deleteAppointmentData(elementId);
                                                    Toast.makeText(v.getContext(), "Deleting Appointment, Please Wait.", Toast.LENGTH_LONG).show();

                                                    new Timer().schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            Intent ii = new Intent(v.getContext(), appointments.class);  // get a valid context
                                                            ii.putExtra("userid", String.valueOf(id));
                                                            ii.putExtra("role", "user");
                                                            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            v.getContext().startActivity(ii);

                                                        }
                                                    }, 2000);
                                                    Toast.makeText(v.getContext(), "Appointment Deleted successfully!!!", Toast.LENGTH_SHORT).show();


                                                } catch (Exception e) {
                                                    Log.e("error", e.getMessage());
                                                }
                                            }
                                        });
                                        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialogDelete.show();
                                    }

                            }
                        });
                        dialog.show();




                    }
                    else {

                    CharSequence[] items = {"View Collector Details", "Delete Appointment"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                    dialog.setTitle("Choose An Option");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {

                            if (item == 0) {
//                                    Toast.makeText(v.getContext(), String.valueOf(elementId), Toast.LENGTH_SHORT).show();
                                DatabaseHelper helper = new DatabaseHelper(v.getContext());

                                SQLiteDatabase db = helper.getReadableDatabase();


                                Cursor cursor = db.rawQuery("SELECT a.App_ID,u.User_Name,u.User_Email,u.User_Contact,a.SC_ID FROM appointments a JOIN users u ON a.SC_ID=u.User_ID WHERE a.App_ID = "+elementId, new String[]{});
                                if (cursor.getCount() > 0) {

                                    cursor.moveToFirst();

                                    do {

                                            number = cursor.getString(3);
                                            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
//set icon
                                                    .setIcon(android.R.drawable.ic_dialog_info)
//set title
                                                    .setTitle("Collector Details")
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
                                // delete
                                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(v.getContext());
                                dialogDelete.setTitle("Warning!!");
                                dialogDelete.setMessage("Are you sure you want to delete your appointment?");
                                dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            DatabaseHelper helper = new DatabaseHelper(v.getContext());
                                            helper.deleteAppointmentData(elementId);
                                            Toast.makeText(v.getContext(), "Deleting Appointment, Please Wait.", Toast.LENGTH_LONG).show();

                                            new Timer().schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Intent ii = new Intent(v.getContext(), appointments.class);  // get a valid context
                                                    ii.putExtra("userid", String.valueOf(id));
                                                    ii.putExtra("role", "user");
                                                    ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    v.getContext().startActivity(ii);

                                                }
                                            }, 2000);
                                            Toast.makeText(v.getContext(), "Appointment Deleted successfully!!!", Toast.LENGTH_SHORT).show();


                                        } catch (Exception e) {
                                            Log.e("error", e.getMessage());
                                        }
                                    }
                                });
                                dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                dialogDelete.show();
                            }



                        }
                    });
                        dialog.show();
                    }
                }
            });




        }

    }
}
