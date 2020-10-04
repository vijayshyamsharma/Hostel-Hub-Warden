package com.jai.hostelappwarden;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeenActivity extends AppCompatActivity {

    String id,time,msg;

    ProgressBar progressBar;

    RecyclerView recyclerView;
    TextView message,datetime;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    SeenAdapter seenAdapter;

    ArrayList<String> arrayList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seen_layout);

        getSupportActionBar().hide();

        arrayList=new ArrayList<>();



        recyclerView=findViewById(R.id.seenlist);
        message=findViewById(R.id.messagetext12);
        datetime=findViewById(R.id.messagedatetime12);
        progressBar=findViewById(R.id.seenprogress);




        Bundle bundle=getIntent().getExtras();

        msg=bundle.get("msg").toString();
        id=bundle.get("msgid").toString();
        time=bundle.get("msgtime").toString();

        message.setText(msg);
        datetime.setText(time);

        firebaseDatabase=FirebaseDatabase.getInstance();

        seenAdapter=new SeenAdapter(this,arrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.viewsshape);
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);
        recyclerView.setAdapter(seenAdapter);

        getData();





    }

    void getData()
    {
        databaseReference=firebaseDatabase.getReference("messageviews").child(id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               /* arrayList.add(dataSnapshot.getKey()+"----"+dataSnapshot.getValue());*/
                arrayList.clear();

                arrayList.add("time");

                Iterable<DataSnapshot> dataSnapshotIterable=dataSnapshot.getChildren();

                for(DataSnapshot snapshot:dataSnapshotIterable)
                {
                    arrayList.add(snapshot.getKey()+"----"+snapshot.getValue());
                }

                seenAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {

        MessageListAdapter.resetallandcopy(0);
        super.onBackPressed();
    }
}
