package crazygames.android.anytimechess;

import android.content.Context;
import android.graphics.Color;
import android.widget.FrameLayout;

public class Square extends FrameLayout {

	public Square(final Context context) {
		super(context);
		setMinimumHeight(100);
		setBackgroundColor(Color.BLACK);
	}

}
