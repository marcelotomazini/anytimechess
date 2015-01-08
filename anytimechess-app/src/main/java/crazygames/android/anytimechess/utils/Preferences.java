package crazygames.android.anytimechess.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preferences {
	
	private Context context;

	public Preferences(Context context) {
		this.context = context;
	}
	
	public SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
