package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.StateMessage;
import crazygames.android.anytimechess.comm.state.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
import crazygames.android.anytimechess.message.SMSSender;
import crazygames.android.anytimechess.utils.Preferences;

public class StateManager {

	private static final String MSG_OUT_OF_ORDER = "State received is old! (Out of order)";
	
	private StateStamp stateStamp;
	private SMSSender sender;

	public StateManager(Context context) {
		this(new StateStamp(new Preferences(context)), new SMSSender());
	}
	
	StateManager(StateStamp stateStamp, SMSSender sender) {
		this.stateStamp = stateStamp;
		this.sender = sender;
	}

	public State create(String player) {
		if (player == null || player.isEmpty())
			throw new RuntimeException("Invalid player identifier");
		
		State state = new State(1, WHITE, new Game());
		stateStamp.setNewStateMessage(new StateMessage(player, state));
		
		return state;
	}

	public State get(String player) {
		String stateMessage = stateStamp.getStateMessage(player);

		return stateMessage == null ? null : new State(stateMessage);
	}

	public State send(String player, State oldState) {
		State state = buildNext(oldState);

		stamp(player, state);
		sender.send(new StateMessage(player, state));
		
		return state;
	}

	private State buildNext(State oldState) {
		int nextSequence = oldState.getTurnSequence() + 1;
		Color newTurn = oldState.getTurn().getReverse();
		
		return new State(nextSequence, newTurn, oldState.getGame());
	}

	public void refresh(String player) {
		State state = get(player);
		
		if (state != null)
			sender.send(new StateMessage(player, state));
	}

	public void update(String player, String messageState) {
		State newState = new State(messageState);
		State oldState = get(player);

		if (oldState == null || isValidStates(oldState, newState))
			stamp(player, newState);
	}
	
	private boolean isValidStates(State oldState, State newState) {
		if (newState.getTurnSequence() < oldState.getTurnSequence())
			throw new RuntimeException(MSG_OUT_OF_ORDER);

		return newState.getTurnSequence() > oldState.getTurnSequence();
	}

	private void stamp(String player, State state) {
		stateStamp.setStateMessage(new StateMessage(player, state));
	}
	
	public boolean isMyTurn(String player) {
		return get(player).getTurn().getTurnValue().equals(getMyColor(player));
	}

	public Color getMyColor(String player) {
		return stateStamp.isSignedState(player) ? Color.WHITE : Color.BLACK;
	}

	public void clear(String player) {
		if (get(player) == null)
			return;
		
		stateStamp.clearStateMessage(player);
	}
}
