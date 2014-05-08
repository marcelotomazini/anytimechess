package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurnTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	private static final String INVALID_TURN = "J";
	private static final String VALID_TURN = "H";
	private static final String VALID_BUILD_TURN = "H";

	@Test
	public void createTurn() {
		Turn turn = new Turn(VALID_TURN);
		
		assertEquals("Turn", VALID_TURN, turn.getTurn());
	}

	@Test
	public void buildTurn() {
		Turn turn = new Turn(VALID_BUILD_TURN, 0);
		
		assertEquals("Turn", VALID_TURN, turn.build());
	}

	@Test
	public void notCreateInvalidTurn() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid turn code");
		
		new Turn(INVALID_TURN);
	}

}
