package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.comm.item.Header.HEADER;
import crazygames.android.anytimechess.comm.item.GameState;
import crazygames.android.anytimechess.comm.item.Header;
import crazygames.android.anytimechess.comm.item.HomePlayer;
import crazygames.android.anytimechess.comm.item.Turn;
import crazygames.android.anytimechess.comm.item.TurnSequence;
import crazygames.android.anytimechess.comm.item.VisitPlayer;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;


public class State implements Message {

	private Header header;
	private TurnSequence turnSeq;
	private HomePlayer home;
	private VisitPlayer visit;
	private Turn turn;
	private GameState gameState;
	
	public State(String messageContext) {
		produce(messageContext);
	}

	public State(int turnSequence, String homePlayer, String visitPlayer, Color turnColor, Game game) {
		header = new Header();
		turnSeq = new TurnSequence(turnSequence);
		home = new HomePlayer(homePlayer);
		visit = new VisitPlayer(visitPlayer);
		turn = new Turn(turnColor);
		gameState = new GameState(game);
	}

	@Override
	public String build() {
		StringBuffer buffer = new StringBuffer();
	
		buffer.append(header.build());
		buffer.append(turnSeq.build());
		buffer.append(home.build());
		buffer.append(visit.build());
		buffer.append(turn.build());
		buffer.append(gameState.build());
		
		return buffer.toString();
	}

	@Override
	public String getDestination() {
		return visit.getPlayer();
	}

	public Color invertTurn() {
		return turn.invert();
	}

	public Game getGame() {
		return new Game(turn.getTurn(), gameState.getMap());
	}

	public String getHeader() {
		return HEADER;
	}

	public int getTurnSequence() {
		return turnSeq.getTurnSequence();
	}
	
	public int nextSequence() {
		return getTurnSequence() + 1;
	}

	public String getHome() {
		return home.getPlayer();
	}
	
	public String getVisit() {
		return visit.getPlayer();
	}

	private void produce(String messageContext) {
		header = new Header();
		int index = header.size();
		
		turnSeq = new TurnSequence(messageContext, index);
		index += turnSeq.size();
		
		home = new HomePlayer(messageContext, index);
		index += home.size();
		
		visit = new VisitPlayer(messageContext, index);
		index += visit.size();
		
		turn = new Turn(messageContext, index);
		index += turn.size();
		
		gameState = new GameState(messageContext, index);
	}
}
