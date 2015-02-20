package crazygames.android.anytimechess.layouts;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.layouts.menu.MenuItem;

public class TitleLayout extends LinearLayout {

	private static final boolean LEFT = false;
	private static final boolean RIGHT = true;
	
	private AnytimeChessActivity anytimeChessActivity;

	public TitleLayout(final AnytimeChessActivity context) {
		super(context);
		anytimeChessActivity = context;
		setBackgroundColor(Color.BLACK);
		
		final int identifier = getResources().getIdentifier("menu", "drawable", context.getPackageName());
		ImageView leftMenu = new ImageView(getContext());
		leftMenu.setImageResource(identifier);
		
		final int listIdentifier = getResources().getIdentifier("list", "drawable", context.getPackageName());
		ImageView rightMenu = new ImageView(getContext());
		rightMenu.setImageResource(listIdentifier);
		
		MenuItem title = new MenuItem(getContext(), "AnytimeChess");
		title.setGravity(Gravity.CENTER);
		
		addView(leftMenu);
		addView(title, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1));
		addView(rightMenu);
		
		leftMenu.setOnClickListener(openMenu(LEFT));
		rightMenu.setOnClickListener(openMenu(RIGHT));
	}

	private OnClickListener openMenu(final boolean left) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				anytimeChessActivity.getSlideMenu().open(left, true);
			}
		};
	}
}
