package crazygames.android.anytimechess.layouts;

<<<<<<< HEAD
import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TableRow;
=======
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TableRow;
import br.com.pilovieira.ermacs.Ermacs;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.utils.Alerts;
>>>>>>> included menu button

public class MainLayout extends LinearLayout {

	private BoardLayout boardLayout;
	private GameStatusLayout gameStatusLayout;
	private ButtonsLayout buttonsLayout;
	private TitleLayout title;

	public MainLayout(final AnytimeChessActivity context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		
		setOrientation(LinearLayout.VERTICAL);
		
		boardLayout = new BoardLayout(getContext());
		gameStatusLayout = new GameStatusLayout(getContext());
		buttonsLayout = new ButtonsLayout(getContext());
		title = new TitleLayout(context);
		
		boardLayout.setStatus(gameStatusLayout);
		
		addView(title);
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
		}
	}
}
