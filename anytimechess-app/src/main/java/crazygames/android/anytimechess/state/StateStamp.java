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

	public void updateState(SmsMessage sms) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(getContactNumber(sms), sms.getMessageBody().toString());
		editor.commit();	
	}

	private SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	private String getContactNumber(SmsMessage sms) {
		//TODO Pilo remove ddd and country code
		return sms.getOriginatingAddress();
	}
}
