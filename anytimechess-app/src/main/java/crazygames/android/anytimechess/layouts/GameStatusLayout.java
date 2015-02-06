package crazygames.android.anytimechess.layouts;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.BLACK;
import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import crazygames.android.anytimechess.comm.state.State;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
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
		createWelcome();
		setBackgroundColor(android.graphics.Color.WHITE);
	}

	private void createWelcome() {
		addView(new WelcomeView(getContext()), new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, new Float(0.50)));
	}

	public void load(String player) {
		createTextView();
		this.player = player;
		refresh();
	}

	private void createTextView() {
		removeAllViews();
		textView = new TextView(getContext());
		textView.setTextColor(android.graphics.Color.BLACK);
		addView(textView);
	}

	public void refresh() {
		state = stateManager.get(player);
		textView.setText(getGameStatus() + "\r\n" + getStatusMessage());
	}

	private String getGameStatus() {
		String playerName = TelephonyUtils.resolvePlayerName(getContext(), player);
		return Messages.getString("game.status", playerName, state.getTurnSequence(), getTurnValue());
	}

	private String getTurnValue() {
		return state.getTurn().getTurnValue() == WHITE ? Messages.getString("white") : Messages.getString("black");
	}
	
	private String getStatusMessage() {
		Color myColor = stateManager.getMyColor(player);

		if (state.getGame().isCheckmate(myColor))
			return Messages.getString("game.over.lose");
		
		if (state.getGame().isCheck(myColor))
			return Messages.getString("you.check");

		Color opponentColor = myColor == WHITE ? BLACK : WHITE;
		
		if (state.getGame().isCheckmate(opponentColor))
			return Messages.getString("game.over.win");
		
		if (state.getGame().isCheck(opponentColor))
			return Messages.getString("other.check");
		
		return stateManager.isMyTurn(player) ? Messages.getString("your.turn") : "";
	}
}
