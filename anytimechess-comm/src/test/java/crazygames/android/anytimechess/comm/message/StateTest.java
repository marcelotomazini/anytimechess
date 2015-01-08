package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;

public class StateTest {

	private static final String SMS_MESSAGE_VALID = "atchess00001004498476508004491257086WHITERNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	private static final String VISIT_PLAYER = "4491257086";
	private static final String HOME_PLAYER = "4498476508";
	private static final Color TURN = WHITE;

	@Rule public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void createState() {
		State state = new State(1, HOME_PLAYER, VISIT_PLAYER, TURN, new Game());
		
		String smsMessage = state.build();
		
		assertEquals("SMS Message", SMS_MESSAGE_VALID, smsMessage);
	}
	
	@Test
	public void createStateWithSMSMessage() {
		State state = new State(SMS_MESSAGE_VALID);
		
		assertEquals("Header", "atchess", state.getHeader());
		assertEquals("TurnSequence", 1, state.getTurnSequence());
		assertEquals("HomePlayer", HOME_PLAYER, state.getHome());
		assertEquals("VisitPlayer", VISIT_PLAYER, state.getVisit());
		assertEquals("Turn", WHITE, state.getGame().getTurn());
		
		assertEquals("GameState", SMS_MESSAGE_VALID, state.build());
	}

	@Test
	public void dontCreateStateWithNullMessage() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Invalid State Message");
		
		new State(null);
	}

	@Test
	public void dontCreateStateWithEmptyMessage() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Invalid State Message");
		
		new State("");
	}
}
