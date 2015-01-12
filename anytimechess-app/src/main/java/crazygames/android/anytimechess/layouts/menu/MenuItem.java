package crazygames.android.anytimechess.layouts.menu;

import android.content.Context;
import android.widget.TextView;

public class MenuItem extends TextView {

	public MenuItem(Context context, String text) {
		super(context);
		setTextSize(20);
		setHeight(80);
		setText(text);
	}

}
