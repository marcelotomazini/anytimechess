package crazygames.android.anytimechess;

import android.content.Context;
import android.graphics.Color;
import android.widget.AbsListView;
import android.widget.FrameLayout;

public class Square extends FrameLayout {

	public Square(final Context context) {
		super(context);
		setBackgroundColor(Color.BLACK);
		setLayoutParams(new AbsListView.LayoutParams(100, 100));
	}

}
