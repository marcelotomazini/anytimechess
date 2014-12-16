package crazygames.android.anytimechess;

import static crazygames.android.anytimechess.state.MyNumber.MY_NUMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.layouts.MainLayout;
import crazygames.android.anytimechess.state.StateManager;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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
		
		TextView tv = new TextView(getContext());
		tv.setText("Jogos"); //TODO Pilo extract string
		
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mainLayout.load(null);
			}
		});
		
		games.add(tv);
		
		return games;
	}

	private TextView buildGame(String player) {
		TextView tv = new TextView(getContext());
		tv.setText(player); //TODO Pilo alterar para nome do contato.
		tv.setOnClickListener(openGame(player));
		return tv;
	}

	private Set<String> getPlayers() {
		Set<String> numbers = PreferenceManager.getDefaultSharedPreferences(getContext()).getAll().keySet();
		
		numbers.remove(MY_NUMBER);
		return numbers;
	}

	private OnClickListener openGame(final String player) {
		return new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				State state = new StateManager(getContext()).get(player);
				mainLayout.load(state);
			}
		};
	}
}
