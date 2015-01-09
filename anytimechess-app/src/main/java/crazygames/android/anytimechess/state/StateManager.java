package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
import crazygames.android.anytimechess.message.SMSSender;
import crazygames.android.anytimechess.utils.Preferences;

public class StateManager {

	private static final String MSG_OUT_OF_ORDER = "State received is old! (Out of order)";
	
	private StateStamp stateStamp;
	private SMSSender sender;
	private MyNumberResolver myNumberResolver;

	public StateManager(Context context) {
		this(new MyNumberResolver(context), new StateStamp(new Preferences(context)), new SMSSender());
	}
	
	StateManager(MyNumberResolver myNumberResolver, StateStamp stateStamp, SMSSender sender) {
		this.myNumberResolver = myNumberResolver;
		this.stateStamp = stateStamp;
		this.sender = sender;
	}

	public State create(String player) {
		String myNumber = myNumberResolver.getMyNumber();
		String playerNumber = filterNumber(player);

		State state = new State(1, myNumber, playerNumber, WHITE, new Game());
		stamp(state);
		
		return state;
	}

	public State get(String player) {
		String stateMessage = stateStamp.getStateMessage(player);

		return stateMessage == null ? null : new State(stateMessage);
	}

	public State send(State oldState, Game newGame) {
		State state = buildNext(oldState, newGame);

		stamp(state);
		send(state);
		
		return state;
	}

	private State buildNext(State oldState, Game game) {
		int nextSequence = oldState.getTurnSequence() + 1;
		Color newTurn = oldState.getTurn().getReverse();
		
		return new State(nextSequence, oldState.getHome(), oldState.getVisit(), newTurn, game);
	}

	public void refresh(String player) {
		State state = get(player);
		
		if (state != null)
			send(state);
	}

	public void update(String messageState) {
		State newState = new State(messageState);
		State oldState = get(getStateKey(newState));

		if (oldState == null || isValidStates(oldState, newState))
			stamp(newState);
	}
	
	private String getStateKey(State state) {
		return filterNumber(state.getHome().contains(myNumberResolver.getMyNumber()) ? state.getVisit() : state.getHome());
	}

	private boolean isValidStates(State oldState, State newState) {
		if (newState.getTurnSequence() < oldState.getTurnSequence())
			throw new RuntimeException(MSG_OUT_OF_ORDER);

		return newState.getTurnSequence() > oldState.getTurnSequence();
	}

	private void stamp(State state) {
		stateStamp.setStateMessage(new StateMessage(getStateKey(state), state));
	}

	private void send(State state) {
		sender.send(new StateMessage(getStateKey(state), state));
	}
}
