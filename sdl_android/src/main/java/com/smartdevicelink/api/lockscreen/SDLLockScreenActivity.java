package com.smartdevicelink.api.lockscreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartdevicelink.R;
import com.smartdevicelink.util.HttpUtils;

import java.io.IOException;

public class SDLLockScreenActivity extends Activity {

	private static final String TAG = "SDLLockScreenActivity";
	private Bitmap lockScreenIcon, lockScreenOEMIcon;
	private ImageView lockscreen_iv;
	private TextView lockscreen_tv;
	private int customView, customIcon;
	public static final String LOCKSCREEN_COLOR_EXTRA = "LOCKSCREEN_COLOR_EXTRA";
	public static final String LOCKSCREEN_BITMAP_EXTRA = "LOCKSCREEN_BITMAP_EXTRA";
	public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";
	public static final String DOWNLOAD_ICON_ACTION = "DOWNLOAD_ICON";

	private final BroadcastReceiver closeLockScreenBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		}
	};

	private final BroadcastReceiver downloadLockScreenIconBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// get data from intent
			if (intent != null) {
				Log.i(TAG, "downloadLockScreenIconBroadcastReceiver called");
				String URL = intent.getStringExtra("URL");
				if (URL != null) {
					downloadLockScreenIcon(URL, null);
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initializeActivity();
		registerReceiver(closeLockScreenBroadcastReceiver, new IntentFilter(CLOSE_LOCK_SCREEN_ACTION));
		registerReceiver(downloadLockScreenIconBroadcastReceiver, new IntentFilter(DOWNLOAD_ICON_ACTION));
	}


	@Override
	protected void onDestroy() {
		unregisterReceiver(closeLockScreenBroadcastReceiver);
		unregisterReceiver(downloadLockScreenIconBroadcastReceiver);
		super.onDestroy();
	}

	public void initializeActivity(){

		// read intent and parse out view, bg color, icon.

		// primitives init with a 0, cant do a null check
		if (customView != 0) {
			setContentView(R.layout.activity_sdllock_screen);
			lockscreen_iv = findViewById(R.id.lockscreen_image);
			lockscreen_tv = findViewById(R.id.lockscreen_text);
			lockScreenIcon = BitmapFactory.decodeResource(getResources(), customIcon);

			// if bg or icon not null, set them
		}else{
			setContentView(customView);
		}
	}

	public void downloadLockScreenIcon(final String url, final LockScreenManager.OnLockScreenIconDownloadedListener lockScreenListener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				try{
					lockScreenOEMIcon = HttpUtils.downloadImage(url);
					if(lockScreenListener != null){
						Log.i(TAG, "Lock Screen Icon Downloaded");
						lockScreenListener.onLockScreenIconDownloaded(lockScreenOEMIcon);
					}
				}catch(IOException e){
					if(lockScreenListener != null){
						Log.e(TAG, "Lock Screen Icon Error Downloading");
						lockScreenListener.onLockScreenIconDownloadError(e);
					}
				}
			}
		}).start();
	}

}
