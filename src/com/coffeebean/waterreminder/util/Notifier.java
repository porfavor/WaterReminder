package com.coffeebean.waterreminder.util;

import java.util.Random;
import java.util.TimerTask;
import java.util.Vector;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.SoundPool;
import android.util.Log;

import com.coffeebean.waterreminder.WaterReminderService;

public class Notifier extends TimerTask {

	private Context ctx;
	private SoundPool soundPool;
	private Vector<Integer> soundBox;

	public Notifier(Context ctx, SoundPool soundPool, Vector<Integer> soundBox) {
		this.ctx = ctx;
		this.soundPool = soundPool;
		this.soundBox = soundBox;
	}

	@Override
	public void run() {
		Log.d(WaterReminderService.class.getSimpleName(),
				"Do notify with vibrate and soundPool play");

		NotificationManager mgr = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification nt = new Notification();
		nt.defaults = Notification.DEFAULT_VIBRATE;// |
													// Notification.DEFAULT_SOUND;
		int notifyId = new Random(System.currentTimeMillis())
				.nextInt(Integer.MAX_VALUE);
		mgr.notify(notifyId, nt);

		// random play soundpool
		int count = soundBox.size();
		int randSoundId = (int) (Math.random() * count);
		soundPool.play(randSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
	}
}
