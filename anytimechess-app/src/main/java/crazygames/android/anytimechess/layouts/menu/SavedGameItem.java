package crazygames.android.anytimechess.layouts.menu;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

public class SavedGameItem extends TextView {

	public SavedGameItem(Context context, String text) {
		super(context);
		setGravity(Gravity.CENTER);
		setHeight(80);
		setText(text);
	}
}
