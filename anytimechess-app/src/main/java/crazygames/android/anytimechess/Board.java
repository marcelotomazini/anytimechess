package crazygames.android.anytimechess;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.GridView;

public class Board extends GridView {

	public Board(final Context context) {
		super(context);

		setBackgroundColor(Color.WHITE);
		//		setVerticalSpacing(5);
		//		setHorizontalSpacing(5);
		setNumColumns(8);
		setColumnWidth(60);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		setGravity(Gravity.CENTER_HORIZONTAL);
		setAdapter(new BoardAdapter(context));
	}


}
