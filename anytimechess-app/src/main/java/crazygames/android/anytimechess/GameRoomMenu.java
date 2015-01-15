package crazygames.android.anytimechess;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import crazygames.android.anytimechess.layouts.MainLayout;
import crazygames.android.anytimechess.layouts.menu.MenuItem;
import crazygames.android.anytimechess.utils.TelephonyUtils;

public class GameRoomMenu extends ListView {
	
	private MainLayout mainLayout;

	public GameRoomMenu(final Activity context, MainLayout mainLayout) {
		super(context);
		this.mainLayout = mainLayout;
		refresh();
	}

	void refresh() {
		List<TextView> games = buildGamesList();
		
		for (String player : getPlayers())
			games.add(buildGame(player));
		
		setAdapter(new SimpleListAdapter(games));
	}

	private ArrayList<TextView> buildGamesList() {
		ArrayList<TextView> games = new ArrayList<TextView>();
		games.add(new MenuItem(getContext(), "Jogos")); //TODO Pilo extract string
		return games;
	}

	private TextView buildGame(String player) {
		TextView tv = new TextView(getContext());
		tv.setText(TelephonyUtils.resolvePlayerName(getContext(), player));
		tv.setOnClickListener(openGame(player));
		return tv;
	}

	private Set<String> getPlayers() {
		return PreferenceManager.getDefaultSharedPreferences(getContext()).getAll().keySet();
	}

	private OnClickListener openGame(final String player) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mainLayout.load(player);
			}
		};
	}
}
