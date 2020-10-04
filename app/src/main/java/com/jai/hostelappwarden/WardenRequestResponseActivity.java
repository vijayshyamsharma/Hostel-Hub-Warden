package com.jai.hostelappwarden;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WardenRequestResponseActivity extends AppCompatActivity {


    TextView name,purpose,time,date,reqtime,replytime,transid,mail,status,wardennote,hostelno;

    Button acceptBtn,denyBtn;

    EditText denyreasontext;

    String refinedmail;

    AlertDialog alertDialog;

    AlertDialog.Builder builder;

    String reasonfordeny;




    UserData userData;

    String statustext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_request_response);

        userData = (UserData) getIntent().getSerializableExtra("objectdata");

        name = findViewById(R.id.studentname1);
        purpose = findViewById(R.id.purposevalue);
        time = findViewById(R.id.touttimevalue);
        date = findViewById(R.id.outdatevalue);
        reqtime = findViewById(R.id.requesttime1);
        replytime = findViewById(R.id.grantedtime1);
        transid = findViewById(R.id.transidvalue);
        mail = findViewById(R.id.useremailvalue1);
        status = findViewById(R.id.wardenresponse1);

        hostelno = findViewById(R.id.hostelvalue);

        acceptBtn = findViewById(R.id.acceptbutton);
        denyBtn = findViewById(R.id.denybutton);


        name.setText(userData.getFullName());
        purpose.setText(userData.getPurpose());

        time.setText(userData.getTime());
        date.setText(userData.getDate());
        hostelno.setText(userData.getHostelName());
        mail.setText(userData.getUsermail());
        transid.setText(userData.getTransid());

        status.setText(userData.getStatus());
        reqtime.setText(userData.getRequestedtime());
        replytime.setText(userData.wardenrespondedtime);

        statustext = userData.getStatus();
        String mail=userData.getUsermail();

        refinedmail=mail.substring(0,mail.indexOf("@"));

        if (statustext.equalsIgnoreCase("accepted")) {

            status.setText("ACCEPTED");
            status.setTextColor(getResources().getColor(R.color.accept));
            status.setVisibility(View.VISIBLE);
            acceptBtn.setVisibility(View.GONE);
            denyBtn.setVisibility(View.GONE);


        } else if (statustext.equalsIgnoreCase("denied")) {

            status.setText("DENIED");
            status.setVisibility(View.VISIBLE);
            status.setTextColor(getResources().getColor(R.color.deny1));
            acceptBtn.setVisibility(View.GONE);
            denyBtn.setVisibility(View.GONE);

        } else {
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    status.setText("ACCEPTED");
                    status.setTextColor(getResources().getColor(R.color.accept));
                    status.setVisibility(View.VISIBLE);
                    acceptBtn.setVisibility(View.GONE);
                    denyBtn.setVisibility(View.GONE);

                    Log.d("usermail",userData.getUsermail());

                    Toast.makeText(WardenRequestResponseActivity.this, "request accepted", Toast.LENGTH_SHORT).show();

                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();


                    DatabaseReference databaseReference = firebaseDatabase.getReference("requeststatus").child(refinedmail).child(userData.getTransid());
                    databaseReference.setValue("accepted");

                    databaseReference = firebaseDatabase.getReference("users").child(refinedmail).child("requests").child(userData.getTransid()).child("status");

                    databaseReference.setValue("accepted");

                    databaseReference = firebaseDatabase.getReference("all requests").child(userData.getTransid()).child("status");
                    databaseReference.setValue("accepted");

                    databaseReference = firebaseDatabase.getReference("users").child(refinedmail).child("requests").child(userData.getTransid()).child("wardenrespondedtime");
                    databaseReference.setValue(getDate(System.currentTimeMillis()));

                    databaseReference = firebaseDatabase.getReference("all requests").child(userData.getTransid()).child("wardenrespondedtime");
                    databaseReference.setValue(getDate(System.currentTimeMillis()));


                }
            });

            denyBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    builder = new AlertDialog.Builder(WardenRequestResponseActivity.this).setView(R.layout.customdialog)
                            .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    status.setVisibility(View.VISIBLE);
                                    status.setTextColor(getResources().getColor(R.color.deny1));
                                    status.setText("DENIED");

                                    acceptBtn.setVisibility(View.GONE);
                                    denyBtn.setVisibility(View.GONE);

                                    denyreasontext = alertDialog.findViewById(R.id.reasontext);

                                    reasonfordeny = denyreasontext.getText().toString();

                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                                    DatabaseReference databaseReference = firebaseDatabase.getReference("requeststatus").child(refinedmail).child(userData.getTransid());
                                    databaseReference.setValue("denied");

                                    databaseReference = firebaseDatabase.getReference("users").child(refinedmail).child("requests").child(userData.getTransid()).child("status");

                                    databaseReference.setValue("denied");

                                    databaseReference = firebaseDatabase.getReference("all requests").child(userData.getTransid()).child("status");
                                    databaseReference.setValue("denied");

                                    databaseReference = firebaseDatabase.getReference("users").child(refinedmail).child("requests").child(userData.getTransid()).child("wardenrespondedtime");
                                    databaseReference.setValue(getDate(System.currentTimeMillis()));

                                    databaseReference = firebaseDatabase.getReference("users").child(refinedmail).child("requests").child(userData.getTransid()).child("ifdenytext");
                                    databaseReference.setValue(reasonfordeny);

                                    databaseReference = firebaseDatabase.getReference("all requests").child(userData.getTransid()).child("wardenrespondedtime");
                                    databaseReference.setValue(getDate(System.currentTimeMillis()));

                                    alertDialog.dismiss();


                                    Toast.makeText(WardenRequestResponseActivity.this, "request denied with reason", Toast.LENGTH_LONG).show();

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Toast.makeText(WardenRequestResponseActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                                }
                            });

                    alertDialog = builder.create();

                    alertDialog.show();


                }
            });


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
