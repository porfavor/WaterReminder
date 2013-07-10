package com.coffeebean.waterreminder;

import java.util.Calendar;
import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.coffeebean.waterreminder.util.Notifier;

public class WaterReminderService extends Service {
	
	private Timer timer;

	// private TimerTask timerTask;

	@Override
	public void onCreate() {
		super.onCreate();

		timer = new Timer();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(WaterReminderService.class.getSimpleName(),
				"Start water reminder service.");

		// timer.schedule(timerTask, 1000, 1000);
		Calendar c = Calendar.getInstance();

		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
		timer.schedule(new Notifier(getBaseContext()), c.getTime());
		Log.d(WaterReminderService.class.getSimpleName(),
				"Notify set at:" + c.getTime());

		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
		timer.schedule(new Notifier(getBaseContext()), c.getTime());
		Log.d(WaterReminderService.class.getSimpleName(),
				"Notify set at:" + c.getTime());

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		timer.cancel();

		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
