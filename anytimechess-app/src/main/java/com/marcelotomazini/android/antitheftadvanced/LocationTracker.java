package com.marcelotomazini.android.antitheftadvanced;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;

public class LocationTracker extends Service implements LocationListener {

	List<Location> locations = new ArrayList<Location>();
	private final IBinder locationTrackerBinder = new LocationTrackerBinder(this);

	@Override
	public void onLocationChanged(Location location) {
		locations.add(location);
	}

	@Override
	public void onProviderDisabled(String paramString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String paramString) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent paramIntent) {
		return locationTrackerBinder;
	}
	
	public Location pop() {
		if(locations.isEmpty())
			return null;
		
		return locations.remove(0);
			
	}

}
