package com.jai.hostelappwarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class BroadcastActivity extends AppCompatActivity {

    static boolean check=false;

    static MenuItem textcpy;
    static  MenuItem seenmenu;

    AlertDialog alertDialog;
    AlertDialog.Builder builder;

    FloatingActionButton fabbtn;

    TextInputEditText messagebox;

    static MessageListAdapter messagecontext;


    Button sendBtn,cancelBtn;

    String uniqueone;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    MessageListAdapter messageListAdapter;

    String texttosend;

    ArrayList<MessageData> arrayList;


    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        getSupportActionBar().setTitle("Broadcasts");
        arrayList=new ArrayList<>();


        fabbtn=findViewById(R.id.fabbtn);
        recyclerView=findViewById(R.id.messagelist);
        progressBar=findViewById(R.id.progressBarMessageMenu);

        messageListAdapter=new MessageListAdapter(this,arrayList);

        messagecontext=messageListAdapter;





        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(messageListAdapter);


        firebaseDatabase=FirebaseDatabase.getInstance();



        getData();















        fabbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder=new AlertDialog.Builder(BroadcastActivity.this).setView(R.layout.broadcastdialog);








                alertDialog=builder.create();
                alertDialog.show();


                sendBtn=alertDialog.findViewById(R.id.buttonsend);
                cancelBtn=alertDialog.findViewById(R.id.buttoncancel);
                messagebox=alertDialog.findViewById(R.id.mainmessagevalue);

                addListener();








            }
        });






    }

    void addListener()
    {
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String val=messagebox.getText().toString();
                if(val.length()!=0 & val!=null & val.isEmpty()==false)
                {
                            databaseReference=firebaseDatabase.getReference("allbroadcasts");

                            uniqueone=databaseReference.push().getKey();

                            databaseReference.child(uniqueone).setValue(new MessageData(uniqueone,val,getDate(System.currentTimeMillis())));


                            alertDialog.dismiss();


                    Toast.makeText(BroadcastActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(BroadcastActivity.this, "type something", Toast.LENGTH_SHORT).show();
                }


            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });
    }


    void getData()
    {
        databaseReference=firebaseDatabase.getReference("allbroadcasts");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arrayList.clear();

                Iterable<DataSnapshot> iterable=dataSnapshot.getChildren();

                for(DataSnapshot snapshot:iterable)
                {
                    arrayList.add(snapshot.getValue(MessageData.class));

                }

                arrayList.add(new MessageData("footer","footer","footer"));



                messageListAdapter.notifyDataSetChanged();

                recyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());








                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    String getDate(long millis)
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        return simpleDateFormat.format(calendar.getTime());
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem menuItem=menu.findItem(R.id.textcopymenu);
        MenuItem seen=menu.findItem(R.id.seenmenu);

        menuItem.setVisible(false);
        seen.setVisible(false);

        textcpy=menuItem;
        seenmenu=seen;

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.copytext,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.textcopymenu)
        {
            MessageListAdapter.resetallandcopy(1);


        }
        else if(id==R.id.seenmenu)
        {
            MessageListAdapter.startseen();
        }
         return true;
    }

    @Override
    public void onBackPressed() {

        if(check==true)
        {
            check=false;
            MessageListAdapter.resetallandcopy(0);
        }
        else {
            super.onBackPressed();
        }

    }
}
