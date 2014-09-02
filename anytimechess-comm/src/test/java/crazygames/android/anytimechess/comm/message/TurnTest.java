package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.BLACK;
import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import static junit.framework.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import crazygames.android.anytimechess.engine.pieces.Piece.Color;


public class TurnTest {
	
	@Rule
	public ExpectedException expected = ExpectedException.none();
	
	private static final Color HOME_TURN = WHITE;
	private static final Color VISIT_TURN = BLACK;
	private static final String VALID_BUILD_TURN = "WHITE";

	@Test
	public void createHomeTurn() {
		Turn turn = new Turn(HOME_TURN);
		
		assertEquals("Turn", HOME_TURN, turn.getTurn());
		assertEquals("Turn", VALID_BUILD_TURN, turn.build());
	}

	@Test
	public void createVisitTurn() {
		Turn turn = new Turn(VISIT_TURN);
		
		assertEquals("Turn", VISIT_TURN, turn.getTurn());
	}

	@Test
	public void buildTurn() {
		Turn turn = new Turn(VALID_BUILD_TURN, 0);
		
		assertEquals("Turn", HOME_TURN, turn.getTurn());
	}
	
	@Test
	public void invertTurn() {
		Turn turn = new Turn(VISIT_TURN);
		
		assertEquals("Turn", VISIT_TURN, turn.getTurn());
		assertEquals("Inverted turn", HOME_TURN, turn.invert());
	}

	@Test
	public void notCreateInvalidTurn() {
		expected.expect(RuntimeException.class);
		expected.expectMessage("Invalid turn code");
		
		new Turn(null);
	}

}
