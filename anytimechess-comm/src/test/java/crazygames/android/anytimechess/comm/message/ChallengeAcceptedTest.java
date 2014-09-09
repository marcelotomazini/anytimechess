package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ChallengeAcceptedTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	private static final String VALID_PLAYER = "4498476508";

	@Test
	public void createChallengeAccepted() {
		ChallengeAccepted ca = new ChallengeAccepted(VALID_PLAYER);
		
		assertEquals("Invite Player", VALID_PLAYER, ca.getDestination());
		assertEquals("Home Player", "atchessHSCA CA", ca.build());
	}

	@Test
	public void doNotCreateChallengeAcceptedWithInvalidDestination() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid destination!");
		
		new ChallengeAccepted(null);
	}

}
