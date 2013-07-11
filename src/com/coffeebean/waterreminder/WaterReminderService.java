package com.coffeebean.waterreminder;

import java.util.Calendar;
import java.util.Timer;
import java.util.Vector;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.coffeebean.waterreminder.util.Notifier;

public class WaterReminderService extends Service implements
		SoundPool.OnLoadCompleteListener {

	private static final int SOUND_LOAD_OK = 1;
	private static SoundPool soundPool;
	private static Vector<Integer> soundBox = new Vector<Integer>();
	private final Handler mHandler = new SoundPoolHandler();

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

		soundPool = new SoundPool(9, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(this);
		soundPool.load(getBaseContext(), R.raw.sound_1, 0);
		soundPool.load(getBaseContext(), R.raw.sound_2, 0);
		soundPool.load(getBaseContext(), R.raw.sound_3, 0);

		// timer.schedule(timerTask, 1000, 1000);
		Calendar c = Calendar.getInstance();

		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
		timer.schedule(new Notifier(getBaseContext(), soundPool, soundBox),
				c.getTime());
		Log.d(WaterReminderService.class.getSimpleName(),
				"Notify set at:" + c.getTime());

		c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);
		timer.schedule(new Notifier(getBaseContext(), soundPool, soundBox),
				c.getTime());
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
				// soundPool.play(msg.arg1, 1.0f, 1.0f, 0, 0, 1.0f);
				soundBox.add(Integer.valueOf(msg.arg1));
				break;
			}
		}
	}
}
