package crazygames.android.anytimechess.comm.message;


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

	public MessageContext(TurnSequence turnSeq, HomePlayer home, VisitPlayer visit, Turn turn, GameState gameState) {
		header = new Header();
		this.turnSeq = turnSeq;
		this.home = home;
		this.visit = visit;
		this.turn = turn;
		this.gameState = gameState;
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

	public GameState getGameState() {
		return gameState;
	}

	public Header getHeader() {
		return header;
	}

	public HomePlayer getHome() {
		return home;
	}

	public Turn getTurn() {
		return turn;
	}

	public TurnSequence getTurnSeq() {
		return turnSeq;
	}

	public VisitPlayer getVisit() {
		return visit;
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
