package crazygames.android.anytimechess.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

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

	public void displayInput(final Inputavel inputavel) {
		Builder alert = new AlertDialog.Builder(context);
		
		final EditText input = new EditText(context);
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
		void input(String text);
		String getMessage();
	}
}
