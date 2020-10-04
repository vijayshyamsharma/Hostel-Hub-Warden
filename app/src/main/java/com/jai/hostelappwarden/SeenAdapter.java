package com.jai.hostelappwarden;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SeenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int HEADER_ITEM=1;

    private  static final int NORMAL_ITEM=0;

    Context context;
    ArrayList<String> arrayList;

    public SeenAdapter(Context context, ArrayList<String> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());

        if(viewType==HEADER_ITEM)
        {
            SeenAdapter.HeaderViewHolder headerViewHolder=new SeenAdapter.HeaderViewHolder(inflater.inflate(R.layout.seenfooter,parent,false));

            return headerViewHolder;
        }
        else{

            SeenAdapter.ImageViewHolder imageViewHolder=new SeenAdapter.ImageViewHolder(inflater.inflate(R.layout.seencustom,parent,false));

            return imageViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SeenAdapter.ImageViewHolder)
        {
            SeenAdapter.ImageViewHolder holder1=(SeenAdapter.ImageViewHolder)holder;


            final String data=arrayList.get(position);

            String arr[]=data.split("----");

            holder1.viewername.setText(arr[0]);
            holder1.viewtime.setText(arr[1]);




        }
        else if(holder instanceof SeenAdapter.HeaderViewHolder)
        {

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(arrayList.get(position).equalsIgnoreCase("time"))
        {
            return HEADER_ITEM;
        }
        else {
            return NORMAL_ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        TextView viewername,viewtime;



        ConstraintLayout constraintLayout;

        public ImageViewHolder(@NonNull final View itemView) {
            super(itemView);

            viewername=itemView.findViewById(R.id.viewername);
            viewtime=itemView.findViewById(R.id.viewtime);


        }
    }



    public static class HeaderViewHolder extends RecyclerView.ViewHolder
    {


        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
