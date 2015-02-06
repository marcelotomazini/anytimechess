package crazygames.android.anytimechess.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class Alerts {
	
	private Context context;

	public Alerts(Context context) {
		this.context = context;
	}
	
	public void displayBundleMessage(String key) {
		displayMessage(Messages.getString(key));
	}
	
	public void displayBundleMessage(String key, Object... params) {
		displayMessage(Messages.getString(key, params));
	}

	public void displayMessage(String message) {
		Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(message);
		alert.setPositiveButton("OK", null);
		alert.show();
	}

	public View.OnClickListener createConfirmListener(final SureListener listener) {
		return new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Alerts(context).displayConfirmSureMessage(listener);
			}
		};
	}

	private void displayConfirmSureMessage(SureListener listener) {
		Builder alert = new AlertDialog.Builder(context);
		alert.setMessage(Messages.getString("are.you.sure"));
		alert.setPositiveButton(Messages.getString("yes"), listener);
		alert.setNegativeButton(Messages.getString("no"), null);
		alert.show();
	}

	public interface SureListener extends DialogInterface.OnClickListener {}
}
