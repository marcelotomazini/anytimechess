package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class TurnTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	private static final String INVALID_TURN = "J";
	private static final String HOME_TURN = "H";
	private static final String VISIT_TURN = "V";
	private static final String VALID_BUILD_TURN = "H";

	@Test
	public void createHomeTurn() {
		Turn turn = new Turn(HOME_TURN);
		
		assertTrue("Should be home turn", turn.isHomeTurn());
		assertFalse("Should not be visit turn", turn.isVisitTurn());
	}

	@Test
	public void createVisitTurn() {
		Turn turn = new Turn(VISIT_TURN);
		
		assertTrue("Should be visit turn", turn.isVisitTurn());
		assertFalse("Should not be home turn", turn.isHomeTurn());
	}

	@Test
	public void buildTurn() {
		Turn turn = new Turn(VALID_BUILD_TURN, 0);
		
		assertEquals("Turn", HOME_TURN, turn.build());
	}

	@Test
	public void notCreateInvalidTurn() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid turn code");
		
		new Turn(INVALID_TURN);
	}

}
