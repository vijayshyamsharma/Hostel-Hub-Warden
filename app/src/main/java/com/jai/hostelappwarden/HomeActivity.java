package com.jai.hostelappwarden;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    ImageView openPermission,openMess,openEnquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Home Page");


        openPermission=findViewById(R.id.opnepermissionactivity);
        openMess=findViewById(R.id.openmessactivity);
        openEnquiry=findViewById(R.id.openenquiryactivity);



        WardenRequestService mSensorService = new WardenRequestService(this);
        Intent mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }



        openPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,LoggedActivity.class);
                startActivity(intent);
            }
        });

        openMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,MessActivity.class);
                startActivity(intent);
            }
        });

        openEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,BroadcastActivity.class);
                startActivity(intent);


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_logged,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        getSharedPreferences("status",MODE_PRIVATE).edit().putBoolean("onlineStatus",false).commit();
        Intent intent=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(intent);
        finish();

        return true;


    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }
}
