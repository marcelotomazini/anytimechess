package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.sms.SMSSender;

public class StateManager {
	
	private Context context;
	
	public StateManager(Context context) {
		this.context = context;
	}
	
	public void create(String player, Game game) {
		State state = new State(1, getHomePlayer(), getVisitPlayer(player), WHITE, game);
		
		firm(state);
	}
	
	public State get(String player) {
		String stateMessage = new StateStamp(context).getStateMessage(player);
		
		return new State(stateMessage);
	}
	
	public void update(String player, Game game) {
		State oldState = get(player);
		State state = buildNext(oldState, game);
		
		firm(state);
	}

	public void refresh(String player) {
		State state = get(player);
		
		firm(state);
	}
	
	public void invite(String player) {
		Challenge invite = new Challenge(getHomePlayer(), getVisitPlayer(player));
		new SMSSender().send(invite);
	}

	private State buildNext(State oldState, Game game) {
		return new State(oldState.nextSequence(), oldState.getHome(), oldState.getVisit(), oldState.invertTurn(), game);
	}

	private void firm(State state) {
		new StateStamp(context).setStateMessage(state);
		new SMSSender().send(state);
	}

	private String getHomePlayer() {
		// TODO Pilo get telephone number
		return StateUtils.filterNumber(null);
	}

	private String getVisitPlayer(String player) {
		return StateUtils.filterNumber(player);
	}
}
