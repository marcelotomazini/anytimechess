package crazygames.android.anytimechess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import br.com.pilovieira.ermacs.Ermacs;
import crazygames.android.anytimechess.layouts.menu.MenuItem;
import crazygames.android.anytimechess.layouts.menu.SavedGameItem;
import crazygames.android.anytimechess.utils.Messages;
import crazygames.android.anytimechess.utils.Preferences;
import crazygames.android.anytimechess.utils.TelephonyUtils;

public class GameRoomMenu extends ListView {
	
	private AnytimeChessActivity activity;

	public GameRoomMenu(final AnytimeChessActivity activity) {
		super(activity);
		this.activity = activity;
		refresh();
	}

	void refresh() {
		List<TextView> games = buildGamesList();
		
		for (String player : getPlayers())
			games.add(buildGame(player));
		
		setAdapter(new SimpleListAdapter(games));
	}

	private Set<String> getPlayers() {
		return new Preferences(getContext()).getSharedPreferences().getAll().keySet();
	}

	private ArrayList<TextView> buildGamesList() {
		ArrayList<TextView> games = new ArrayList<TextView>();
		games.add(new MenuItem(getContext(), Messages.getString("games")));
		return games;
	}

	private TextView buildGame(String player) {
		if (player.equals(Ermacs.ERMACS))
			return Ermacs.getTextView(getContext());
		
		SavedGameItem item = new SavedGameItem(getContext(), TelephonyUtils.resolvePlayerName(getContext(), player));
		item.setOnClickListener(openGame(player));
		return item;
	}

	private OnClickListener openGame(final String player) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.getMainLayout().load(player);
				activity.getSlideMenu().close(true);
			}
		};
	}
}
