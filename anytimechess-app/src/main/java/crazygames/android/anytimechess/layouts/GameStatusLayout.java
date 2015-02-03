package crazygames.android.anytimechess.layouts;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Messages;
import crazygames.android.anytimechess.utils.TelephonyUtils;

public class GameStatusLayout extends LinearLayout {

	private TextView textView;
	private StateManager stateManager;
	private String player;
	private State state;

	public GameStatusLayout(Context context) {
		super(context);

		stateManager = new StateManager(getContext());
	}

	public void load(String player) {
		createTextView();
		this.player = player;
		refresh();
	}

	private void createTextView() {
		removeAllViews();
		textView = new TextView(getContext());
		addView(textView);
	}

	public void refresh() {
		state = stateManager.get(player);
		textView.setText(getGameStatus() + "\r\n" + getTurnMessage());
	}

	private String getGameStatus() {
		String playerName = TelephonyUtils.resolvePlayerName(getContext(), player);
		return Messages.getString("game.status", playerName, state.getTurnSequence(), getTurnValue());
	}

	private String getTurnValue() {
		return state.getTurn().getTurnValue() == WHITE ? Messages.getString("white") : Messages.getString("black");
	}
	
	private String getTurnMessage() {
		return stateManager.isMyTurn(player, state) ? Messages.getString("your.turn") : "";
	}
}
