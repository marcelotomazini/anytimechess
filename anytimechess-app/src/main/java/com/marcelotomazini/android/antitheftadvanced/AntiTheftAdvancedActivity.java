package com.marcelotomazini.android.antitheftadvanced;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class AntiTheftAdvancedActivity extends Activity {

	public static final String ACTIVATE_MOBILE_DATA = "activateMobileData";
	public static final String ACTIVATE_WIFI = "activateWifi";
	
	private Button btnActivate;
	private SharedPreferences prefs;
	private CheckBox chkWifi;
	private CheckBox chkMobileData;
	
	private LocationTracker locationTracker;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences(AntiTheftAdvanced.APP_PACKAGE, Context.MODE_PRIVATE);
        setContentView(create());
    }
	
	@Override
	protected void onStart() {
		startService(new Intent("com.marcelotomazini.android.antitheftadvanced.LocationTracker"));
		
		ServiceConnection connection = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName paramComponentName) {
				locationTracker = null;
			}
			
			@Override
			public void onServiceConnected(ComponentName paramComponentName, IBinder service) {
				locationTracker = ((LocationTrackerBinder) service).locationTracker();
				
				while(locationTracker != null) {
					Location location = locationTracker.pop();
					if(location != null)
						postLocation(location);
				}
			}

			private String postLocation(Location location) {
				Map<String,String> httpParameters = new HashMap<String,String>(); 
				httpParameters.put("id", prefs.getString(Configuration.ID, null));
				httpParameters.put("lat", "" + location.getLatitude());
				httpParameters.put("lon", "" + location.getLongitude());
				httpParameters.put("time", "" + location.getTime());
				httpParameters.put("speed", "" + location.getSpeed());
				try {
					return HttpUtil.post(prefs.getString(Configuration.LOCATION_RECEIVER_URL, null), httpParameters);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	private LinearLayout create() {
		TextView txt = new TextView(this);
		txt.setText("Configuration");
		
		chkWifi = new CheckBox(this);
		chkWifi.setText("Activate WIFI automatically");
		chkWifi.setContentDescription("Activate WIFI to send the location and turn off again");
		chkWifi.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				prefs.edit().putBoolean(ACTIVATE_WIFI, chkWifi.isChecked()).commit();
			}
		});

		chkMobileData = new CheckBox(this);
		chkMobileData.setText("Activate Mobile Data automatically");
		chkMobileData.setContentDescription("Activate Mobile Data to send the location and turn off again");
		chkMobileData.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				prefs.edit().putBoolean(ACTIVATE_MOBILE_DATA, chkMobileData.isChecked()).commit();
			}
		});
		
		NumberPicker periodicity = new NumberPicker(this);
		periodicity.setContentDescription("Defines the frequency that the location is send (in minutes)");
		periodicity.setMinValue(0);
		periodicity.setMaxValue(60);
		periodicity.setValue(15);
		
		btnActivate = new Button(this);
		btnActivate.setText(isActivated() ? "Deactivate" : "Activate");
		btnActivate.setOnClickListener(new OnClickListener() {
			@Override public void onClick(View v) {
				prefs.edit().putBoolean("active", !isActivated()).commit();
				btnActivate.setText(isActivated() ? "Deactivate" : "Activate");
			}
		});
		
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.addView(txt);
		layout.addView(chkWifi);
		layout.addView(chkMobileData);
		layout.addView(periodicity);
		layout.addView(btnActivate);
		
		loadSettings();
		
		return layout;
	}

	public void loadSettings() {
		chkWifi.setChecked(prefs.getBoolean(ACTIVATE_WIFI, false));
		chkMobileData.setChecked(prefs.getBoolean(ACTIVATE_MOBILE_DATA, false));
	}

	private boolean isActivated() {
		return prefs.getBoolean("active", false);
	}
}
