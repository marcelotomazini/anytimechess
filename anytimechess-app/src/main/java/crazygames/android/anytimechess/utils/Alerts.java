package crazygames.android.anytimechess.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;

public class Alerts {
	
	private Context context;

	public Alerts(Context context) {
		this.context = context;
	}

	public void displayMessage(String message) {
		Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setPositiveButton("OK", null);
		alert.show();
	}
}
