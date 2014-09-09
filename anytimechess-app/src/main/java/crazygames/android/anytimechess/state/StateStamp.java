package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import crazygames.android.anytimechess.comm.message.Message;

public class StateStamp {
	
	private Context context;
	
	public StateStamp(Context context) {
		this.context = context;
	}
	
	public String getStateMessage(String player) {
		return getSharedPreferences().getString(filterNumber(player), null);
	}

	public void setStateMessage(Message message) {
		setStateMessage(message.getDestination(), message.build());
	}

	private void setStateMessage(String player, String stateMessage) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(filterNumber(player), stateMessage);
		editor.commit();	
	}

	private SharedPreferences getSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
