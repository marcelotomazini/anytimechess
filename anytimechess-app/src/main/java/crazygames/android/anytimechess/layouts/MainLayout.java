package crazygames.android.anytimechess.layouts;

import crazygames.android.anytimechess.utils.Alerts;
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TableRow;
import br.com.pilovieira.ermacs.Ermacs;

public class MainLayout extends LinearLayout {

	private BoardLayout boardLayout;
	private GameStatusLayout gameStatusLayout;
	private ButtonsLayout buttonsLayout;

	public MainLayout(final Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		
		setOrientation(LinearLayout.VERTICAL);
		
		boardLayout = new BoardLayout(getContext());
		gameStatusLayout = new GameStatusLayout(getContext());
		buttonsLayout = new ButtonsLayout(getContext());
		
		boardLayout.setStatus(gameStatusLayout);
		
		addView(boardLayout);
		addView(gameStatusLayout, new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, new Float(0.50)));
		addView(buttonsLayout);
	}
	
	public void load(String player) {
		try {
			gameStatusLayout.load(player);
			boardLayout.load(player);
			buttonsLayout.load(player);
		} catch (Exception e) {
			Ermacs.addErmac(getContext(), e);
			new Alerts(getContext()).displayMessage("ERMAC!");
		}
	}
}
