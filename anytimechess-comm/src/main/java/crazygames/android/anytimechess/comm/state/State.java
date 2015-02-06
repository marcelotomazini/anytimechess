package crazygames.android.anytimechess.comm.state;

import static crazygames.android.anytimechess.comm.state.Header.HEADER;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;

public class State {

	private Header header;
	private TurnSequence turnSeq;
	private Turn turn;
	private GameState gameState;
	private Game game;

	public State(String messageContext) {
		validate(messageContext);
		produce(messageContext);
	}

	public State(int turnSequence, Color turnColor, Game game) {
		header = new Header();
		turnSeq = new TurnSequence(turnSequence);
		turn = new Turn(turnColor);
		gameState = new GameState(game);
	}

	public String build() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(header.build());
		buffer.append(turnSeq.build());
		buffer.append(turn.build());
		buffer.append(gameState.build());

		return buffer.toString();
	}

	public Turn getTurn() {
		return turn;
	}

	public Game getGame() {
		if(game == null)
			game = new Game(turn.getTurnValue(), gameState.getMap());
		return game;
	}

	public String getHeader() {
		return HEADER;
	}

	public int getTurnSequence() {
		return turnSeq.getTurnSequence();
	}

	private void validate(String messageContext) {
		if (messageContext == null || messageContext.isEmpty())
			throw new RuntimeException("Invalid State Message");
	}

	private void produce(String messageContext) {
		header = new Header();
		int index = header.size();

		turnSeq = new TurnSequence(messageContext, index);
		index += turnSeq.size();

		turn = new Turn(messageContext, index);
		index += turn.size();

		gameState = new GameState(messageContext, index);
	}
}
