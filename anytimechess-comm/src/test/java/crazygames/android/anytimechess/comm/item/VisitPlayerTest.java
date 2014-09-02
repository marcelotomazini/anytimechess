package crazygames.android.anytimechess.comm.item;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class VisitPlayerTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	private static final String BUILD_VALID_PLAYER = "004498476508";
	private static final String VALID_PLAYER = "4498476508";

	@Test
	public void createVisitPlayer() {
		VisitPlayer VisitPlayer = new VisitPlayer(VALID_PLAYER);
		
		assertEquals("Home Player", VALID_PLAYER, VisitPlayer.getVisitPlayer());
	}

	@Test
	public void createVisitPlayerMessage() {
		String messageSequence = BUILD_VALID_PLAYER;
		
		VisitPlayer VisitPlayer = new VisitPlayer(messageSequence, 0);
		
		assertEquals("Home Player", VALID_PLAYER, VisitPlayer.getVisitPlayer());
	}

	@Test
	public void buildVisitPlayer() {
		VisitPlayer VisitPlayer = new VisitPlayer(VALID_PLAYER);
		
		assertEquals("Home Player build", BUILD_VALID_PLAYER, VisitPlayer.build());
	}
	
	@Test
	public void notCreateVisitPlayerWithInvalidIdentifier() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid player identifier");
		
		new VisitPlayer("123456789123456789");
	}
}
