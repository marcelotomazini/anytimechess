package crazygames.android.anytimechess.comm.item;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class HomePlayerTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();

	private static final String BUILD_VALID_PLAYER = "004498476508";
	private static final String VALID_PLAYER = "4498476508";

	@Test
	public void createHomePlayer() {
		HomePlayer homePlayer = new HomePlayer(VALID_PLAYER);
		
		assertEquals("Home Player", VALID_PLAYER, homePlayer.getHomePlayer());
	}

	@Test
	public void createHomePlayerMessage() {
		String messageSequence = BUILD_VALID_PLAYER;
		
		HomePlayer homePlayer = new HomePlayer(messageSequence, 0);
		
		assertEquals("Home Player", VALID_PLAYER, homePlayer.getHomePlayer());
	}

	@Test
	public void buildHomePlayer() {
		HomePlayer homePlayer = new HomePlayer(VALID_PLAYER);
		
		assertEquals("Home Player build", BUILD_VALID_PLAYER, homePlayer.build());
	}
	
	@Test
	public void notCreateHomePlayerWithInvalidIdentifier() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid player identifier");
		
		new HomePlayer("123456789123456789");
	}
}
