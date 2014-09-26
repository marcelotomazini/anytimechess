package crazygames.android.anytimechess.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class NotificationUtils {

	private static Context context;

	public static void init(Context context) {
		NotificationUtils.context = context;
	}
	
	public static void displayMessage(String message) {
		Builder ok = new AlertDialog.Builder(context);
        ok.setMessage(message);
        ok.setPositiveButton("OK", null);
        ok.show();
	}
}
