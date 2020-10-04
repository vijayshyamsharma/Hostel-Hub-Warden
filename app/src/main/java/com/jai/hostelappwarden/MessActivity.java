package com.jai.hostelappwarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessActivity extends AppCompatActivity {

    TextInputLayout outerlabel;

    MenuMess menuMess;

    String weekday;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    TextInputEditText breakfastfood,lunchfood,dinnerfood;

    String breakfastdetail,lunchdetail,dinnerdetail;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        Toast.makeText(this, "Editing mode off", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "to edit first click the edit button", Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle("Edit Mess Menu");

        breakfastfood=findViewById(R.id.breakfastfood);
        lunchfood=findViewById(R.id.lunchfood);
        dinnerfood=findViewById(R.id.dinnerfood);
        spinner=findViewById(R.id.spinnerweekdays);

        outerlabel=findViewById(R.id.menubreakfast);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                weekday=parent.getSelectedItem().toString();

                breakfastfood.setEnabled(false);
                lunchfood.setEnabled(false);
                dinnerfood.setEnabled(false);
                outerlabel.setEnabled(false);

                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firebaseDatabase=FirebaseDatabase.getInstance();















    }

    void getData()
    {
        databaseReference=firebaseDatabase.getReference("messmenudetails").child(weekday);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                menuMess=dataSnapshot.getValue(MenuMess.class);

                if(menuMess==null)
                {
                    Toast.makeText(MessActivity.this, "menu for this weekday doesn't exist", Toast.LENGTH_LONG).show();
                    breakfastfood.setText("");
                    lunchfood.setText("");
                    dinnerfood.setText("");

                }
                else {
                    breakfastfood.setText(menuMess.getBreakfastmenu());
                    lunchfood.setText(menuMess.getLunchmenu());
                    dinnerfood.setText(menuMess.getDinnermenu());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mess,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.editmenu)
        {
            Toast.makeText(this, "Editing mode on", Toast.LENGTH_SHORT).show();

            breakfastfood.setEnabled(true);
            lunchfood.setEnabled(true);
            dinnerfood.setEnabled(true);
            outerlabel.setEnabled(true);

        }
        else if(id==R.id.savemenu)
        {


            databaseReference=firebaseDatabase.getInstance().getReference("messmenudetails").child(weekday);


            breakfastdetail=breakfastfood.getText().toString();
            lunchdetail=lunchfood.getText().toString();
            dinnerdetail=dinnerfood.getText().toString();

            if(breakfastdetail.length()!=0 & lunchdetail.length()!=0 & dinnerdetail.length()!=0)
            {
                MenuMess menuMess1=new MenuMess(breakfastdetail,dinnerdetail,lunchdetail);

                databaseReference.setValue(menuMess1);

                Toast.makeText(this, "menu details saved", Toast.LENGTH_SHORT).show();

                breakfastfood.setEnabled(false);
                lunchfood.setEnabled(false);
                dinnerfood.setEnabled(false);
                outerlabel.setEnabled(false);

                Toast.makeText(this, "Editing mode off", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(this, "fill all required details", Toast.LENGTH_SHORT).show();
            }






        }

        return true;
    }



}
