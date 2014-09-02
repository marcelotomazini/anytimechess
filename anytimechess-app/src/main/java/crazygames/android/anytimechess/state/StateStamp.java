package crazygames.android.anytimechess.state;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

public class StateStamp {
	
	private Context context;
	
	public StateStamp(Context context) {
		this.context = context;
	}
	
	public String getState(String player) {
		return getSharedPreferences().getString(filterContactNumber(player), null);
	}

	public void setState(SmsMessage sms) {
		setState(sms.getOriginatingAddress(), sms.getMessageBody().toString());
	}

	public void setState(String player, String state) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(filterContactNumber(player), state);
		editor.commit();	
	}

	private SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private String filterContactNumber(String player) {
		//TODO Pilo remove ddd and country code
		return player;
	}
}
