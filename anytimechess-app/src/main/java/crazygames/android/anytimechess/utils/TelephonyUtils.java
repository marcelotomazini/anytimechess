package crazygames.android.anytimechess.utils;

import android.content.Context;
import crazygames.android.anytimechess.state.MyNumberResolver;


public class TelephonyUtils {
	
	public static String filterNumber(String playerNumber) {
		if (playerNumber == null)
			return null;
		
		playerNumber = playerNumber.replace("(", "");
		playerNumber = playerNumber.replace(")", "");
		playerNumber = playerNumber.replace("-", "");
		playerNumber = playerNumber.trim();
		
		int length = playerNumber.length();
		return playerNumber.substring(length < 8 ? 0 : length - 8, length);
	}
	
	public static String getTelephonyNumber(Context context) {
		return new MyNumberResolver(context).getMyNumber();
	}
}
