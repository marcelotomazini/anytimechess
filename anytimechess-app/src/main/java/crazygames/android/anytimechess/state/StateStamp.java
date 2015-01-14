package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import crazygames.android.anytimechess.comm.message.Message;
import crazygames.android.anytimechess.utils.Preferences;

public class StateStamp {
	
	private Preferences preferences;
	
	public StateStamp(Preferences preferences) {
		this.preferences = preferences;
	}
	
	public String getStateMessage(String player) {
		return getSharedPreferences().getString(filterNumber(player), null);
	}

	public void setStateMessage(Message message) {
		setStateMessage(message.getDestination(), message.build());
	}

	public void clearStateMessage(String player) {
		Editor editor = getSharedPreferences().edit();
		editor.remove(player);
		editor.commit();
	}

	private void setStateMessage(String player, String stateMessage) {
		Editor editor = getSharedPreferences().edit();
		editor.putString(filterNumber(player), stateMessage);
		editor.commit();	
	}

	private SharedPreferences getSharedPreferences() {
		return preferences.getSharedPreferences();
	}
}
