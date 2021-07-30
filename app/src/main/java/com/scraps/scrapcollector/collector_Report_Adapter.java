package com.scraps.scrapcollector;

import android.app.AlertDialog;
import android.content.Context;
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

public class collector_Report_Adapter extends RecyclerView.Adapter<collector_Report_Adapter.collector_Report_Holder> {
    private Scrap_Adapter.OnItemClickListener mListener;
    private ArrayList<collectorReportClass> report=new ArrayList<collectorReportClass>();
    private String role;
    private int id;
    String number;
    Context context;

    public collector_Report_Adapter(ArrayList<collectorReportClass> report,String role,int id) {
        this.report = report;
        this.role=role;
        this.id=id;
    }

    @NonNull
    @NotNull
    @Override
    public collector_Report_Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.collectorreportlist,parent,false);
        return new collector_Report_Adapter.collector_Report_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull collector_Report_Adapter.collector_Report_Holder holder, int position) {
        collectorReportClass mv= report.get(position);

        if(role.equals("admin")) {
        holder.date.setText("Date : " +mv.getDate());
        holder.username.setText("Collector : " +mv.getUsername());
        holder.contact.setText("Contact : " +mv.getUsercontact());
        holder.type.setText("Type : " +mv.getScraptype());
        holder.weight.setText("Weight : " +mv.getWeight() + " Kg");
        holder.price.setText("Price : " +String.valueOf(mv.getTotalprice()) + " Rs");
        }
        else {
            holder.date.setText("Date : " +mv.getDate());
            holder.username.setText("User : " +mv.getUsername());
            holder.contact.setText("Contact : " +mv.getUsercontact());
            holder.type.setText("Type : " +mv.getScraptype());
            holder.weight.setText("Weight : " +mv.getWeight() + " Kg");
            holder.price.setText("Price : " +String.valueOf(mv.getTotalprice()) + " Rs");
        }

    }

    @Override
    public int getItemCount() {
        return report.size();
    }

    public class collector_Report_Holder extends RecyclerView.ViewHolder {
        TextView date;
        TextView username;
        TextView contact;
        TextView type;
        TextView weight;
        TextView price;
        ImageView edit;
        public collector_Report_Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            date= (TextView) itemView.findViewById(R.id.reportdate);
            username= (TextView) itemView.findViewById(R.id.reportusername);
            contact= (TextView) itemView.findViewById(R.id.reportcontact);
            type= (TextView) itemView.findViewById(R.id.reportscraptype);
            weight= (TextView) itemView.findViewById(R.id.reportweight);
            price= (TextView) itemView.findViewById(R.id.reporttotalprice);
            edit=(ImageView) itemView.findViewById(R.id.editreport);

            if(role.equals("collector")) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int elementId = report.get(getAdapterPosition()).getSaleid();

                            CharSequence[] items = {"Call User"};
                            AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                            dialog.setTitle("Choose An Option");
                            dialog.setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {

                                    if (item == 0) {
//                                    Toast.makeText(v.getContext(), String.valueOf(elementId), Toast.LENGTH_SHORT).show();
                                        DatabaseHelper helper = new DatabaseHelper(v.getContext());

                                        SQLiteDatabase db = helper.getReadableDatabase();


                                        Cursor cursor = db.rawQuery("SELECT u.User_Contact FROM sales s JOIN appointments a ON a.App_ID=s.App_ID JOIN users u ON u.User_ID=a.User_ID WHERE s.Sale_ID = "+elementId, new String[]{});
                                        if (cursor.getCount() > 0) {

                                            cursor.moveToFirst();

                                            do {

                                                number = cursor.getString(0);

                                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                                intent.setData(Uri.parse("tel:" + number));
                                                                v.getContext().startActivity(intent);
                                                                //set what would happen when positive button is clicked


                                            }
                                            while (cursor.moveToNext());
                                        }


                                    }


                                }
                            });
                            dialog.show();

                    }
                });
            }
            else if(role.equals("user")) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int elementId = report.get(getAdapterPosition()).getSaleid();

                        CharSequence[] items = {"Call Collector"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                        dialog.setTitle("Choose An Option");
                        dialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (item == 0) {
//                                    Toast.makeText(v.getContext(), String.valueOf(elementId), Toast.LENGTH_SHORT).show();
                                    DatabaseHelper helper = new DatabaseHelper(v.getContext());

                                    SQLiteDatabase db = helper.getReadableDatabase();


                                    Cursor cursor = db.rawQuery("SELECT u.User_Contact FROM sales s JOIN appointments a ON a.App_ID=s.App_ID JOIN users u ON u.User_ID=a.SC_ID WHERE s.Sale_ID = "+elementId, new String[]{});
                                    if (cursor.getCount() > 0) {

                                        cursor.moveToFirst();

                                        do {

                                            number = cursor.getString(0);

                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:" + number));
                                            v.getContext().startActivity(intent);
                                            //set what would happen when positive button is clicked


                                        }
                                        while (cursor.moveToNext());
                                    }


                                }


                            }
                        });
                        dialog.show();

                    }
                });
            }
            else if(role.equals("admin")) {
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int elementId = report.get(getAdapterPosition()).getSaleid();

                        CharSequence[] items = {"Call Collector"};
                        AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                        dialog.setTitle("Choose An Option");
                        dialog.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {

                                if (item == 0) {
//                                    Toast.makeText(v.getContext(), String.valueOf(elementId), Toast.LENGTH_SHORT).show();
                                    DatabaseHelper helper = new DatabaseHelper(v.getContext());

                                    SQLiteDatabase db = helper.getReadableDatabase();


                                    Cursor cursor = db.rawQuery("SELECT u.User_Contact FROM sales s JOIN appointments a ON a.App_ID=s.App_ID JOIN users u ON u.User_ID=a.SC_ID WHERE s.Sale_ID = "+elementId, new String[]{});
                                    if (cursor.getCount() > 0) {

                                        cursor.moveToFirst();

                                        do {

                                            number = cursor.getString(0);

                                            Intent intent = new Intent(Intent.ACTION_DIAL);
                                            intent.setData(Uri.parse("tel:" + number));
                                            v.getContext().startActivity(intent);
                                            //set what would happen when positive button is clicked


                                        }
                                        while (cursor.moveToNext());
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
}
