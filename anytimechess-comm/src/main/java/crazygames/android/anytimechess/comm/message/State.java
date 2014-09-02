package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.comm.message.Header.HEADER;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece;


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

	public State(int turnSequence, String homePlayer, String visitPlayer, String turnCode, Game game) {
		header = new Header();
		turnSeq = new TurnSequence(turnSequence);
		home = new HomePlayer(homePlayer);
		visit = new VisitPlayer(visitPlayer);
		turn = new Turn(turnCode);
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

	public String getHeader() {
		return HEADER;
	}

	public int getTurnSequence() {
		return turnSeq.getTurnSequence();
	}

	public String getHome() {
		return home.getPlayer();
	}
	
	public String getVisit() {
		return visit.getPlayer();
	}

	public boolean isHomeTurn() {
		return turn.isHomeTurn();
	}

	public boolean isVisitTurn() {
		return turn.isVisitTurn();
	}

	public Piece[][] getGameMap() {
		return gameState.getMap();
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
