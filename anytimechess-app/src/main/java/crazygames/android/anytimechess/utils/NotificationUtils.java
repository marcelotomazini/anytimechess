package crazygames.android.anytimechess.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

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

	public static void displayInput(final Inputavel inputavel) {
		Builder alert = new AlertDialog.Builder(inputavel.getContext());
		
		final EditText input = new EditText(inputavel.getContext());
		alert.setView(input);
		
		alert.setMessage(inputavel.getMessage());
		alert.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String text = input.getText().toString();
				inputavel.input(text);
			}
		});
		
		alert.show();
	}
	
	public interface Inputavel {		
		Context getContext();
		void input(String text);
		String getMessage();
	}
}
