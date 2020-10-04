package com.jai.hostelappwarden;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class WardenRequestService extends Service {

    String CHANNEL_ID = "my_channel_01";
    int notifyID = 1;

    Notification notification;
    NotificationManager mNotificationManager;


    Context context;

    public WardenRequestService(Context applicationContext) {
        super();
        context = applicationContext;
        Log.i("HERE", "here service created!");
    }

    public WardenRequestService() {
    }






    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("HERE", "here service created!");




        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all requests");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserData userData;
                if(dataSnapshot.exists())
                {
                    Iterable<DataSnapshot> dataSnapshotIterable=dataSnapshot.getChildren();

                    for(DataSnapshot snapshot:dataSnapshotIterable)
                    {
                        userData=(UserData) snapshot.getValue(UserData.class);

                        if (userData.getStatus().equalsIgnoreCase("waiting"))
                        {
                            createNotification();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);



    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent iHeartBeatService = new Intent(WardenRequestService.this,
                WardenRequestService.class);
        PendingIntent piHeartBeatService = PendingIntent.getService(this, 0,
                iHeartBeatService, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(piHeartBeatService);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), 1000, piHeartBeatService);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();






    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    void createNotification() {

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(getBaseContext(), LoggedActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(getBaseContext())
                .setAutoCancel(true).setSmallIcon(R.drawable.hostel)


                .setContentText("a student is requesting for outing")
                .setContentTitle("NEW REQUEST AVAILABLE")
                .setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setChannelId(CHANNEL_ID);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            CharSequence name = "Message";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.enableLights(true);

            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{150, 100, 150, 100});

            mChannel.setShowBadge(true);


            mNotificationManager.createNotificationChannel(mChannel);


        }

        notification = builder.build();


        mNotificationManager.notify(notifyID, notification);

    }



}
