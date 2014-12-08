package crazygames.android.anytimechess.utils;

import static android.content.Context.TELEPHONY_SERVICE;
import android.content.Context;
import android.telephony.TelephonyManager;


public class TelephonyUtils {
	
	public static String filterNumber(String playerNumber) {
		playerNumber = playerNumber.replace("(", "");
		playerNumber = playerNumber.replace(")", "");
		playerNumber = playerNumber.replace("-", "");
		playerNumber = playerNumber.trim();
		
		int length = playerNumber.length();
		return playerNumber.substring(length < 8 ? 0 : length - 8, length);
	}
	
	public static String getTelephonyNumber(Context context) {
		TelephonyManager tm = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE); 
		return filterNumber(tm.getLine1Number());
	}

}
