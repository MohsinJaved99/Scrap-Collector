package com.scraps.scrapcollector;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class ScrapCollector_Adapter extends RecyclerView.Adapter<ScrapCollector_Adapter.ScrapCollector_Holder> {
    private Scrap_Adapter.OnItemClickListener mListener;
    private ArrayList<ScrapCollectors> scrapcollectorr=new ArrayList<ScrapCollectors>();
    private String role;
    private int id;
    Context context;

    public ScrapCollector_Adapter(ArrayList<ScrapCollectors> scrapcollectorr,String role,int id) {
        this.scrapcollectorr = scrapcollectorr;
        this.role=role;
        this.id=id;
    }

    @NonNull
    @NotNull
    @Override
    public ScrapCollector_Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.collectorlist,parent,false);
        return new ScrapCollector_Adapter.ScrapCollector_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScrapCollector_Adapter.ScrapCollector_Holder holder, int position) {
        ScrapCollectors mv= scrapcollectorr.get(position);
        holder.scrapcollectorname.setText(mv.getScrapcollectorname());
        holder.scrapcollectoremail.setText(mv.getScrapcollectoremail());
        holder.scrapcollectorcontact.setText(mv.getScrapcollectorcontact());
        holder.scrapcollectorregdate.setText(mv.getScrapcollectorregdate());
    }

    @Override
    public int getItemCount() {
        return scrapcollectorr.size();
    }


    public class ScrapCollector_Holder extends RecyclerView.ViewHolder {
        TextView scrapcollectorname;
        TextView scrapcollectoremail;
        TextView scrapcollectorcontact;
        TextView scrapcollectorregdate;
        ImageView edit;
        public ScrapCollector_Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            scrapcollectorname= (TextView) itemView.findViewById(R.id.scrapcollectornametxt);
            scrapcollectoremail= (TextView) itemView.findViewById(R.id.scrapcollectoremail);
            scrapcollectorcontact= (TextView) itemView.findViewById(R.id.scrapcollectorcontact);
            scrapcollectorregdate= (TextView) itemView.findViewById(R.id.scrapcollectorregdate);
            edit=(ImageView) itemView.findViewById(R.id.editscrapcollector);

//            if(role.equals("admin")) {
//                itemView.findViewById(R.id.editscrap).setVisibility(View.VISIBLE);
//            }
//            else {
//                itemView.findViewById(R.id.editscrap).setVisibility(View.GONE);
//            }




            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = scrapcollectorr.get(getAdapterPosition()).getScrapcollectorid();
                    CharSequence[] items = {"Delete Collector"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                    dialog.setTitle("Choose An Option");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                // delete
                                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(v.getContext());
                                dialogDelete.setTitle("Warning!!");
                                dialogDelete.setMessage("Are you sure you want to this delete?");
                                dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            DatabaseHelper helper=new DatabaseHelper(v.getContext());
                                            helper.deleteCollectorData(elementId);
                                            Toast.makeText(v.getContext(), "Deleting Collector, Please Wait.",Toast.LENGTH_LONG).show();

                                            new Timer().schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Intent ii = new Intent(v.getContext(), Scrap_Collector.class);  // get a valid context
                                                    ii.putExtra("userid",String.valueOf(id));
                                                    ii.putExtra("role","admin");
                                                    ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    v.getContext().startActivity(ii);

                                                }
                                            }, 2000);
                                            Toast.makeText(v.getContext(), "Collector Deleted successfully!!!",Toast.LENGTH_SHORT).show();


                                        } catch (Exception e){
                                            Toast.makeText(v.getContext(), "Collector Can't Be Deleted.",Toast.LENGTH_SHORT).show();
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
            });




        }
    }
}
