package crazygames.android.anytimechess.state;

import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import crazygames.android.anytimechess.comm.message.Message;
import crazygames.android.anytimechess.utils.Preferences;

public class StateStamp {
	
	private static final String SIGN = "HOME";
	private Preferences preferences;
	
	public StateStamp(Preferences preferences) {
		this.preferences = preferences;
	}
	
	public void clearStateMessage(String player) {
		Editor editor = getSharedPreferences().edit();
		editor.remove(player);
		editor.commit();
	}

	public String getStateMessage(String player) {
		for (String component : getCompositeStateMessage(player))
			if (!SIGN.equals(component))
				return component;
		
		return null;
	}

	public boolean isSignedState(String player) {
		return getCompositeStateMessage(player).size() == 2;
	}

	public void setNewStateMessage(Message message) {
		HashSet<String> compositeMessage = new HashSet<String>();
		compositeMessage.add(message.build());
		compositeMessage.add(SIGN);
		
		setStateMessage(message.getDestination(), compositeMessage);
	}

	public void setStateMessage(Message message) {
		HashSet<String> compositeMessage = new HashSet<String>();
		compositeMessage.add(message.build());
		
		Set<String> old = getCompositeStateMessage(message.getDestination());
		if (old.size() == 2)
			compositeMessage.add(SIGN);
		
		setStateMessage(message.getDestination(), compositeMessage);
	}
	
	private void setStateMessage(String player, Set<String> compositeMessage) {
		Editor editor = getSharedPreferences().edit();
		editor.putStringSet(player, compositeMessage);
		editor.commit();
	}

	private Set<String> getCompositeStateMessage(String player) {
		return getSharedPreferences().getStringSet(player, new HashSet<String>());
	}

	private SharedPreferences getSharedPreferences() {
		return preferences.getSharedPreferences();
	}
}
