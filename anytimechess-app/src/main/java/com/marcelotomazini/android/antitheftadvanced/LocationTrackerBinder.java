package com.marcelotomazini.android.antitheftadvanced;

import android.os.Binder;


public class LocationTrackerBinder extends Binder {

	private LocationTracker locationTracker;

	public LocationTrackerBinder(LocationTracker locationTracker) {
		this.locationTracker = locationTracker;
	}
	
	public LocationTracker locationTracker() {
		return locationTracker;
	}

}
