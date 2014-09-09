package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import static crazygames.android.anytimechess.utils.TelephonyUtils.getTelephonyNumber;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.message.SMSSender;

public class StateManager {
	
	private Context context;
	
	public StateManager(Context context) {
		this.context = context;
	}
	
	public void create(String player) {
		State state = new State(1, getTelephonyNumber(context), filterNumber(player), WHITE, new Game());
		
		stamp(state);
		//TODO Pilo notify
	}

	public State get(String player) {
		String stateMessage = new StateStamp(context).getStateMessage(player);
		
		return new State(stateMessage);
	}
	
	public void send(String player, Game game) {
		State oldState = get(player);
		State state = buildNext(oldState, game);
		
		stamp(state);
		new SMSSender().send(state);
	}

	public void refresh(String player) {
		State state = get(player);
		
		new SMSSender().send(state);
	}
	
	public void update(String messageState) {
		State newState = new State(messageState);
		State oldState = get(newState.getVisit());
		
		if (newState.getTurnSequence() < oldState.getTurnSequence())
			throw new RuntimeException("Inconsistent State");
		
		if (newState.getTurnSequence() > oldState.getTurnSequence())
			stamp(newState);
		
		//TODO Pilo notify
	}
	
	private State buildNext(State oldState, Game game) {
		return new State(oldState.nextSequence(), oldState.getHome(), oldState.getVisit(), oldState.invertTurn(), game);
	}

	private void stamp(State state) {
		new StateStamp(context).setStateMessage(state);
	}
}
