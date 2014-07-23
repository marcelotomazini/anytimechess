package crazygames.android.anytimechess.comm.message;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece;


public class MessageContext {

	private Header header;
	private TurnSequence turnSeq;
	private HomePlayer home;
	private VisitPlayer visit;
	private Turn turn;
	private GameState gameState;
	
	public MessageContext(String messageContext) {
		produce(messageContext);
	}

	public MessageContext(int turnSequence, String homePlayer, String visitPlayer, String turnCode, Game game) {
		header = new Header();
		turnSeq = new TurnSequence(turnSequence);
		home = new HomePlayer(homePlayer);
		visit = new VisitPlayer(visitPlayer);
		turn = new Turn(turnCode);
		gameState = new GameState(game);
	}
	
	public String buildMessage() {
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
		return (String) header.getHeaderValue();
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
