package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import crazygames.android.anytimechess.BoardAdapter;

public class MainLayout extends LinearLayout {

	private BoardLayout boardLayout;
	private BoardAdapter boardAdapter;
	private GameStatusLayout gameStatusLayout;

	public MainLayout(final Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		
		setOrientation(LinearLayout.VERTICAL);
		
		boardLayout = new BoardLayout(context);
		gameStatusLayout = new GameStatusLayout(context);
		addView(boardLayout);
		addView(gameStatusLayout);
	}
	
	public void load(String player) {
		boardAdapter = new BoardAdapter(getContext());
		boardLayout.setAdapter(boardAdapter);
		boardLayout.load(player);
	}
}
