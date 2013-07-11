package com.coffeebean.waterreminder;

import com.coffeebean.waterreminder.util.CustomDbManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
	TextView firstOne, secondOne, thirdOne, fourthOne, fifthOne, sixthOne,
			seventhOne, eighthOne;
	CustomDbManager dbMgr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);

		setContentView(R.layout.activity_water_reminder);

		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.titlebar);

		firstOne = (TextView) this.findViewById(R.id.firstOne);
		secondOne = (TextView) this.findViewById(R.id.secondOne);
		thirdOne = (TextView) this.findViewById(R.id.thirdOne);
		fourthOne = (TextView) this.findViewById(R.id.fourthOne);
		fifthOne = (TextView) this.findViewById(R.id.fifthOne);
		sixthOne = (TextView) this.findViewById(R.id.sixthOne);
		seventhOne = (TextView) this.findViewById(R.id.seventhOne);
		eighthOne = (TextView) this.findViewById(R.id.eighthOne);

		firstOne.setOnClickListener(this);
		secondOne.setOnClickListener(this);
		thirdOne.setOnClickListener(this);
		fourthOne.setOnClickListener(this);
		fifthOne.setOnClickListener(this);
		sixthOne.setOnClickListener(this);
		seventhOne.setOnClickListener(this);
		eighthOne.setOnClickListener(this);
		
		dbMgr = new CustomDbManager(this);

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

	private void showDialog_Layout(Context context, String text, int src) {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View timeSetView = inflater.inflate(R.layout.time_dialog, null);
		final TimePicker timePicker = (TimePicker) timeSetView
				.findViewById(R.id.timePicker);

		final TextView tv = (TextView) this.findViewById(src);

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

	@Override
	public void onClick(View v) {
		int src = v.getId();
		
		TextView tv = (TextView) this.findViewById(src);
		showDialog_Layout(this, tv.getText().toString(), src);

//		switch (src) {
//		case R.id.firstOne:
//			showDialog_Layout(this, firstOne.getText().toString(), src);
//			break;
//
//		case R.id.secondOne:
//			showDialog_Layout(this, secondOne.getText().toString(), src);
//			break;
//
//		case R.id.thirdOne:
//			showDialog_Layout(this, thirdOne.getText().toString(), src);
//			break;
//
//		default:
//		}
	}
}
