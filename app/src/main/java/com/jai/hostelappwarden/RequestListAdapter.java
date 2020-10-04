package com.jai.hostelappwarden;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.view.View.GONE;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ImageViewHolder> {

    String refinedemail;

    AlertDialog.Builder builder;

    AlertDialog alertDialog;


    TextView denyreasontext;

    String reasonfordeny;

    ArrayList<UserData> data;

    Context context;

    public RequestListAdapter(ArrayList<UserData> arrayList, Context context)
    {


        data=arrayList;


        this.context=context;

    }


    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final UserData userData=data.get(position);





        holder.studentname.setText(userData.getFullName());

        holder.useremail.setText(userData.getUsermail());

        holder.requestTime.setText(userData.getRequestedtime());
        holder.respondTime.setText(userData.getWardenrespondedtime());

        String mail=userData.getUsermail();





        String status=userData.getStatus();

        holder.wardenresponse.setText("NOT ANSWERED");
        holder.wardenresponse.setTextColor(Color.BLACK);

        if(status.equalsIgnoreCase("accepted"))
        {

            holder.wardenresponse.setText("ACCEPTED");
            holder.wardenresponse.setTextColor(context.getResources().getColor(R.color.accept));


        }
        else if (status.equalsIgnoreCase("denied"))
        {

            holder.wardenresponse.setText("DENIED");
            holder.wardenresponse.setTextColor(context.getResources().getColor(R.color.deny1));

        }

        holder.allDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context,WardenRequestResponseActivity.class);
                intent.putExtra("objectdata",userData);
                context.startActivity(intent);
            }
        });



    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customrequestlist,parent,false);

        ImageViewHolder imageViewHolder=new ImageViewHolder(view);

        return imageViewHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {

        TextView studentname,useremail,wardenresponse,requestTime,respondTime,allDetails;



        ConstraintLayout constraintLayout;




        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            studentname=itemView.findViewById(R.id.studentname);


            useremail=itemView.findViewById(R.id.useremailvalue);


            wardenresponse=itemView.findViewById(R.id.wardenresponse);

            requestTime=itemView.findViewById(R.id.requesttime);
            respondTime=itemView.findViewById(R.id.grantedtime);


            allDetails=itemView.findViewById(R.id.viewalldetails);




        }
    }

    String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }
}
