package crazygames.android.anytimechess.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;


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
	
	
	public static String resolvePlayerName(Context context, String player) {
		ContentResolver contentResolver = context.getContentResolver();
		String[] projection = new String[]{ Phone.DISPLAY_NAME };
		String selection = Phone.NUMBER + " LIKE " + player;
		
		Cursor cursor = contentResolver.query(Phone.CONTENT_URI, projection, selection, null, null);
		
		if (cursor.moveToFirst())
			return cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
		
		return player;
	}
}
