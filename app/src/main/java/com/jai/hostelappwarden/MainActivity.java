package com.jai.hostelappwarden;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    String adminid;
    EditText phone,code;
    Button login,otp;

    String verification;
    String code1;
    String phone1;


    FirebaseAuth auth;

    boolean loginstatus=false;

    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        phone = findViewById(R.id.emaillogin);
        code=findViewById(R.id.editText2);
        login = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        otp=findViewById(R.id.button);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("adminid").child("admin email1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adminid=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("signing in");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);





        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                phone1=phone.getText().toString();

                if(phone1.equalsIgnoreCase(adminid)==true)
                {
                    if(phone1.length()!=0 & phone1.contains("@")){

                        auth.sendPasswordResetEmail(phone1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(MainActivity.this, "check email for password change", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                    else {
                        Toast.makeText(MainActivity.this, "first enter email", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "only warden has access", Toast.LENGTH_SHORT).show();
                }


            }
        });









        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                code1=code.getText().toString();
                phone1=phone.getText().toString();


                if(phone1.equalsIgnoreCase(adminid)==true)
                {
                    if (phone1.contains("@") && code1.length()!=0) {

                        auth.signInWithEmailAndPassword(phone1,code1).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful())
                                {
                                    Intent intent=new Intent(MainActivity.this,HomeActivity.class);

                                    startActivity(intent);
                                    finish();

                                    SharedPreferences sharedPreferences=getSharedPreferences("status",MODE_PRIVATE);

                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putBoolean("onlineStatus",true);
                                    editor.putString("emailid",phone1);

                                    editor.commit();




                                }
                                else {
                                    Toast.makeText(MainActivity.this, "not a user", Toast.LENGTH_SHORT).show();
                                    progressDialog.cancel();
                                }

                            }
                        });






                    }
                    else {
                        progressDialog.cancel();
                        Toast.makeText(MainActivity.this, "Invalid Details...", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this, "only warden has access", Toast.LENGTH_SHORT).show();
                }




            }
        });




    }
}
