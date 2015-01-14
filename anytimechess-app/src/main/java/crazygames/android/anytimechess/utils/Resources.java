package crazygames.android.anytimechess.utils;

import android.content.Context;

public class Resources {
	
	private Context context;

	public Resources(Context context) {
		this.context = context;
	}
	
	public int getIdentifierAppName() {
		return context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
	}
	
	public int getMainIcon() {
		return context.getResources().getIdentifier("chess", "drawable", context.getPackageName());
	}
}
