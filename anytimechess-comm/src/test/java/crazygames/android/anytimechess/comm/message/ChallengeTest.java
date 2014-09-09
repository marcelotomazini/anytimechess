package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ChallengeTest {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	private static final String VALID_PLAYER = "4498476508";

	@Test
	public void createChallenge() {
		Challenge invite = new Challenge("Pilo", VALID_PLAYER);
		
		assertEquals("Invite Player", VALID_PLAYER, invite.getDestination());
		assertEquals("Home Player", "atchessHSCH Hey you! Pilo is challenging you to play AnytimeChess!", invite.build());
	}

	@Test
	public void createInviteWithoutPlayerName() {
		Challenge invite = new Challenge(null, VALID_PLAYER);
		
		assertEquals("Invite Player", VALID_PLAYER, invite.getDestination());
		assertEquals("Home Player", "atchessHSCH Hey you! This is an challenge to play AnytimeChess!", invite.build());
	}
	
	@Test
	public void doNotCreateInviteWithInvalidDestination() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid destination!");
		
		new Challenge(null, null);
	}

}
