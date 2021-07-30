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

public class Scrap_Adapter extends RecyclerView.Adapter<Scrap_Adapter.Scrap_Holder> {
    private OnItemClickListener mListener;
    private ArrayList<Scrap> scrap=new ArrayList<Scrap>();
    private String role;
    private int id;
    Context context;
    public Scrap_Adapter(ArrayList<Scrap> scrap,String role,int id) {
        this.scrap = scrap;
        this.role=role;
        this.id=id;
    }
    public interface OnItemClickListener {
        void onItemClick(int elementId, View v);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    @NonNull
    @NotNull
    @Override
    public Scrap_Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.scraplist,parent,false);
        return new Scrap_Holder(view);
    }
    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
    scraps m2;
    @Override
    public void onBindViewHolder(@NonNull @NotNull Scrap_Adapter.Scrap_Holder holder, int position) {
        Scrap mv= scrap.get(position);
        holder.scrapname.setText(mv.getScrapname());
        holder.scrapprice.setText(mv.getScrapprice() + " Rs / Kg");
        holder.scrapdisc.setText(mv.getScrapdis());
        byte[] Image = mv.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(Image, 0, Image.length);
        holder.img.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return scrap.size();
    }

    public class Scrap_Holder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView scrapname;
        TextView scrapprice;
        TextView scrapdisc;
        ImageView edit;
        public Scrap_Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.movieimg);
            scrapname= (TextView) itemView.findViewById(R.id.scrapnametxt);
            scrapprice= (TextView) itemView.findViewById(R.id.scrappricetxt);
            scrapdisc= (TextView) itemView.findViewById(R.id.scrapdistxt);
            edit=(ImageView) itemView.findViewById(R.id.editscrap);

            if(role.equals("admin")) {
                itemView.findViewById(R.id.editscrap).setVisibility(View.VISIBLE);
            }
            else {
                itemView.findViewById(R.id.editscrap).setVisibility(View.GONE);
            }




            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = scrap.get(getAdapterPosition()).getScrapid();
                    CharSequence[] items = {"Update Scrap", "Delete Scrap"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());

                    dialog.setTitle("Choose An Option");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                // update
                                Intent i = new Intent(v.getContext(), update_scrap.class);  // get a valid context
                                i.putExtra("userid", String.valueOf(id));
                                i.putExtra("role", "admin");
                                i.putExtra("scrapid",elementId);
                                v.getContext().startActivity(i);

                            } else {
                                // delete
                                final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(v.getContext());
                                dialogDelete.setTitle("Warning!!");
                                dialogDelete.setMessage("Are you sure you want to this delete?");
                                dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            DatabaseHelper helper=new DatabaseHelper(v.getContext());
                                            helper.deleteData(elementId);
                                            Toast.makeText(v.getContext(), "Deleting Scrap, Please Wait.",Toast.LENGTH_LONG).show();
                                            new Timer().schedule(new TimerTask() {
                                                @Override
                                                public void run() {
                                                    Intent ii = new Intent(v.getContext(), scraps.class);  // get a valid context
                                                    ii.putExtra("userid",String.valueOf(id));
                                                    ii.putExtra("role","admin");
                                                    ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    v.getContext().startActivity(ii);

                                                }
                                            }, 2000);
                                            Toast.makeText(v.getContext(), "Scrap Deleted successfully!!!",Toast.LENGTH_SHORT).show();

                                        } catch (Exception e){
                                            Toast.makeText(v.getContext(), "Scrap Can't Be Deleted.",Toast.LENGTH_SHORT).show();
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
