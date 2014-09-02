package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;

public class StateTest {

	private static final String SMS_MESSAGE_VALID = "atchess00001004498476508004491257086WHITERNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	private static final String VISIT_PLAYER = "4491257086";
	private static final String HOME_PLAYER = "4498476508";
	private static final Color TURN = WHITE;

	@Test
	public void createSMSMessage() {
		State messageContext = new State(1, HOME_PLAYER, VISIT_PLAYER, TURN, new Game());
		
		String smsMessage = messageContext.build();
		
		assertEquals("SMS Message", SMS_MESSAGE_VALID, smsMessage);
	}
	
	@Test
	public void createMessageContext() {
		State state = new State(SMS_MESSAGE_VALID);
		
		assertEquals("Header", "atchess", state.getHeader());
		assertEquals("TurnSequence", 1, state.getTurnSequence());
		assertEquals("HomePlayer", HOME_PLAYER, state.getHome());
		assertEquals("VisitPlayer", VISIT_PLAYER, state.getVisit());
		assertEquals("Turn", WHITE, state.getGame().getTurn());
		
		assertEquals("GameState", SMS_MESSAGE_VALID, state.build());
	}
}
