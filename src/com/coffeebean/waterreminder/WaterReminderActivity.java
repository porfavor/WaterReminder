package com.coffeebean.waterreminder;

import java.util.Hashtable;

import com.coffeebean.waterreminder.common.Constants;
import com.coffeebean.waterreminder.util.CustomDbManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 
 * @author CoffeeBean
 * 
 */
public class WaterReminderActivity extends Activity implements OnClickListener {
	private int number = Constants.NUMBER;
	private TextView[] textView = new TextView[number];
	private CustomDbManager dbMgr;
	private int[] hour = new int[number];
	private int[] minute = new int[number];
	private Hashtable<Integer, Integer> rId2Index = new Hashtable<Integer, Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_water_reminder);

		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.titlebar);

		dbMgr = new CustomDbManager(this);
		dbMgr.open();

		checkForInitData();

		initReminderView();

		Intent intent = new Intent();
		intent.setClass(WaterReminderActivity.this, WaterReminderService.class);
		startService(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.water_reminder, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		Intent intent = new Intent();
		intent.setClass(WaterReminderActivity.this, WaterReminderService.class);
		stopService(intent);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		int src = v.getId();

		TextView tv = (TextView) this.findViewById(src);
		showDialog_Layout(this, tv.getText().toString(), src);

		// switch (src) {
		// case R.id.firstOne:
		// showDialog_Layout(this, firstOne.getText().toString(), src);
		// break;
		//
		// case R.id.secondOne:
		// showDialog_Layout(this, secondOne.getText().toString(), src);
		// break;
		//
		// case R.id.thirdOne:
		// showDialog_Layout(this, thirdOne.getText().toString(), src);
		// break;
		//
		// default:
		// }
	}

	private void checkForInitData() {
		int i = 0, num = Constants.NUMBER;
		int countDb = 0;
		Cursor cursor;

		cursor = dbMgr.queryData(Constants.DATA_TABLE,
				new String[] { "count(*)" }, null);
		if (cursor != null && 0 == cursor.getCount()) {
			if (cursor.moveToFirst()) {
				countDb = cursor.getInt(cursor.getColumnIndex("count(*)"));

				Log.d(WaterReminderActivity.class.getSimpleName(),
						"read countDb=" + countDb);
			}
		}

		if (0 == countDb) {
			ContentValues cv;
			for (i = 0; i < num; ++i) {

				cv = new ContentValues();
				cv.put("idx", i);
				cv.put("hour", Constants.DEFAULT_HOUR + 2 * i);
				cv.put("minute", 0);

				dbMgr.insertData(Constants.DATA_TABLE, cv);

				Log.d(WaterReminderActivity.class.getSimpleName(),
						"insert to " + Constants.DATA_TABLE + ":[" + i + "]("
								+ cv.toString() + ")");
			}
		} else {
			for (i = 0; i < num; ++i) {
				cursor = dbMgr.queryData(Constants.DATA_TABLE, new String[] {
						"idx", "hour", "minute" }, "idx='" + i + "'");

				if (cursor.moveToFirst()) {
					hour[i] = cursor.getInt(cursor
							.getColumnIndexOrThrow("hour"));
					minute[i] = cursor.getInt(cursor
							.getColumnIndexOrThrow("minute"));

					Log.d(WaterReminderActivity.class.getSimpleName(),
							"read data " + Constants.DATA_TABLE + ":[" + i
									+ "](" + hour[i] + ":" + minute[i] + ")");
				}
			}
		}
	}

	private void initReminderView() {
		textView[0] = (TextView) this.findViewById(R.id.firstOne);
		textView[1] = (TextView) this.findViewById(R.id.secondOne);
		textView[2] = (TextView) this.findViewById(R.id.thirdOne);
		textView[3] = (TextView) this.findViewById(R.id.fourthOne);
		textView[4] = (TextView) this.findViewById(R.id.fifthOne);
		textView[5] = (TextView) this.findViewById(R.id.sixthOne);
		textView[6] = (TextView) this.findViewById(R.id.seventhOne);
		textView[7] = (TextView) this.findViewById(R.id.eighthOne);

		rId2Index.put(Integer.valueOf(R.id.firstOne), Integer.valueOf(0));
		rId2Index.put(Integer.valueOf(R.id.secondOne), Integer.valueOf(1));
		rId2Index.put(Integer.valueOf(R.id.thirdOne), Integer.valueOf(2));
		rId2Index.put(Integer.valueOf(R.id.fourthOne), Integer.valueOf(3));
		rId2Index.put(Integer.valueOf(R.id.fifthOne), Integer.valueOf(4));
		rId2Index.put(Integer.valueOf(R.id.sixthOne), Integer.valueOf(5));
		rId2Index.put(Integer.valueOf(R.id.seventhOne), Integer.valueOf(6));
		rId2Index.put(Integer.valueOf(R.id.eighthOne), Integer.valueOf(7));

		String zero = "";
		for (int i = 0; i < number; ++i) {
			if (minute[i] < 10) {
				zero = "0";
			} else {
				zero = "";
			}

			textView[i].setText(Constants.PREFIX[i] + hour[i] + ":" + zero
					+ minute[i]);
			textView[i].setOnClickListener(this);
		}
	}

	private void showDialog_Layout(Context context, String text, int src) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View timeSetView = inflater.inflate(R.layout.time_dialog, null);
		final TextView tv = (TextView) this.findViewById(src);

		final TimePicker timePicker = (TimePicker) timeSetView
				.findViewById(R.id.timePicker);
		timePicker.setCurrentHour(hour[rId2Index.get(Integer.valueOf(src))]);
		timePicker
				.setCurrentMinute(minute[rId2Index.get(Integer.valueOf(src))]);

		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setCancelable(false);
		// builder.setIcon(R.drawable.icon); customize icon
		builder.setTitle(text);
		builder.setView(timeSetView);
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				setTitle(timePicker.getCurrentHour() + " : "
						+ timePicker.getCurrentMinute());

				tv.setText(timePicker.getCurrentHour() + " : "
						+ timePicker.getCurrentMinute());
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				setTitle("");
			}
		});
		builder.show();
	}
}
