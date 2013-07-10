package org.kernby.readinglight;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private boolean isScreenLocked;
	SeekBar seekBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
        setContentView(R.layout.activity_main);
		RelativeLayout view = (RelativeLayout)findViewById(R.id.main);
		seekBar = (SeekBar)findViewById(R.id.seekBar1);
		
		view.setKeepScreenOn(true);
		view.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if (isScreenLocked) {
					seekBar.setEnabled(true);
					isScreenLocked = !isScreenLocked;
					Toast.makeText(getApplicationContext(), "Screen unlocked", Toast.LENGTH_SHORT).show();
				}else {
					seekBar.setEnabled(false);
					isScreenLocked = !isScreenLocked;
					Toast.makeText(getApplicationContext(), "Screen Locked", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
		
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
				progress = Math.max(progress, 1);
			    layoutParams.screenBrightness = progress / 100.0f;
			    getWindow().setAttributes(layoutParams);
				
			}
		});
		isScreenLocked = false;
	}
	
	
	@Override
	public void onBackPressed() {
//		super.onBackPressed();
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			AlertDialog.Builder alBuilder = new AlertDialog.Builder(this);
			alBuilder.setTitle(R.string.app_name);
			alBuilder.setMessage(R.string.menu_message);
			alBuilder.setPositiveButton("FeedBack", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					final String appName = "org.kernby.readinglight";
					try {
					    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+appName)));
					} catch (android.content.ActivityNotFoundException e) {
					    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+appName)));
					}
				}
			});
			alBuilder.setNeutralButton("Ok", new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			AlertDialog dialog = alBuilder.create();
			dialog.show();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
