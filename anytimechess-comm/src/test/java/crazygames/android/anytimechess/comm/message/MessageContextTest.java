package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Test;

import crazygames.android.anytimechess.engine.game.Game;

public class MessageContextTest {

	private static final String SMS_MESSAGE_VALID = "atchess00001004498476508004491257086HRNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	private static final String VISIT_PLAYER = "4491257086";
	private static final String HOME_PLAYER = "4498476508";
	private static final String TURN = "H";

	@Test
	public void createSMSMessage() {
		MessageContext messageContext = new MessageContext(1, HOME_PLAYER, VISIT_PLAYER, TURN, new Game());
		
		String smsMessage = messageContext.buildMessage();
		
		assertEquals("SMS Message", SMS_MESSAGE_VALID, smsMessage);
	}
	
	@Test
	public void createMessageContext() {
		MessageContext messageContext = new MessageContext(SMS_MESSAGE_VALID);
		
		assertEquals("Header", "atchess", messageContext.getHeader());
		assertEquals("TurnSequence", 1, messageContext.getTurnSequence());
		assertEquals("HomePlayer", HOME_PLAYER, messageContext.getHome());
		assertEquals("VisitPlayer", VISIT_PLAYER, messageContext.getVisit());
		assertTrue("Should be home turn", messageContext.isHomeTurn());
		assertFalse("Should not be visit turn", messageContext.isVisitTurn());
		
		assertEquals("GameState", SMS_MESSAGE_VALID, messageContext.buildMessage());
	}
}
