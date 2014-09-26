package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameStatusLayout extends LinearLayout {

	private TextView child;

	public GameStatusLayout(Context context) {
		super(context);
		child = new TextView(context);
		child.setText("Usefull informations about the game");
		addView(child);
	}

	public void refresh(String msg) {
		child.setText(msg);
	}
}
