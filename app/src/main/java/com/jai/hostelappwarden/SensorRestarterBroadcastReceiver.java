package com.jai.hostelappwarden;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SensorRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("msg","service stopped");
        context.startService(new Intent(context, WardenRequestService.class));

    }
}
