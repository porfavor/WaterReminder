package com.coffeebean.waterreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    
    public void onReceive(Context context, Intent i) {
            context.startService(new Intent(context, WaterReminderService.class));
    }
}