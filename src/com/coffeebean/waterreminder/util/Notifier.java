package com.coffeebean.waterreminder.util;

import java.util.Random;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.coffeebean.waterreminder.WaterReminderService;
import com.coffeebean.waterreminder.R;

public class Notifier extends TimerTask implements
		SoundPool.OnLoadCompleteListener {

	private static final int SOUND_LOAD_OK = 1;

	private final Handler mHandler = new SoundPoolHandler();

	private Context ctx;
	private static SoundPool soundPool;

	public Notifier(Context ctx) {
		this.ctx = ctx;
	}

	@Override
	public void run() {
		Log.d(WaterReminderService.class.getSimpleName(),
				"Do notify with sound and vibrate");

		NotificationManager mgr = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification nt = new Notification();
		nt.defaults = Notification.DEFAULT_VIBRATE ;//| Notification.DEFAULT_SOUND;
		int notifyId = new Random(System.currentTimeMillis())
				.nextInt(Integer.MAX_VALUE);
		mgr.notify(notifyId, nt);

		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(this);
		soundPool.load(ctx, R.raw.dial, 0);
	}

	@Override
	public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
		Log.d(WaterReminderService.class.getSimpleName(), "onLoadComplete");
		Message msg = mHandler.obtainMessage(SOUND_LOAD_OK);
		msg.arg1 = sampleId;
		mHandler.sendMessage(msg);
	}

	private static class SoundPoolHandler extends Handler {
		public void handleMessage(Message msg) {
			Log.d(WaterReminderService.class.getSimpleName(),
					"handleMessage msg = " + msg.what);

			switch (msg.what) {
			case SOUND_LOAD_OK:
				soundPool.play(msg.arg1, 1.0f, 1.0f, 0, 0, 1.0f);
				break;
			}
		}
	}

}
