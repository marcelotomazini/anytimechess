package crazygames.android.anytimechess.state;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import crazygames.android.anytimechess.utils.NotificationUtils;
import crazygames.android.anytimechess.utils.NotificationUtils.Inputavel;

public class MyNumberResolver implements Inputavel {
	
	public static final String MY_NUMBER = "myNumber";
	private Context context;
	
	public MyNumberResolver(Context context) {
		this.context = context;
	}
	
	public void resolveMyNumber() {
		if (getSharedPreferences().contains(MY_NUMBER))
			return;
		
		setMyNumber();
	}

	public void setMyNumber() {
		new NotificationUtils(context).displayInput(this);
	}
	
	public String getMyNumber() {
		resolveMyNumber();
		return getSharedPreferences().getString(MY_NUMBER, null);
	}
	
	private void refreshMyNumber(String myNumber) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(MY_NUMBER, myNumber);
		editor.commit();	
	}

	private SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	@Override
	public void input(String text) {
		refreshMyNumber(text);
	}

	@Override
	public String getMessage() {
		return "Digite seu número"; //TODO Pilo extract string
	}
}