package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GiveUpTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	private static final String VALID_PLAYER = "4498476508";

	@Test
	public void createGiveUp() {
		GiveUp ca = new GiveUp(VALID_PLAYER);
		
		assertEquals("Destination player", VALID_PLAYER, ca.getDestination());
		assertEquals("Home Player", "atchessGIVEUP", ca.build());
	}

	@Test
	public void doNotCreateChallengeAcceptedWithInvalidDestination() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid destination!");
		
		new GiveUp(null);
	}

}
