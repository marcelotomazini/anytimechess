package crazygames.android.anytimechess.comm.message;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

public class TurnSequenceTest {

	@Test
	public void createTurnSequence() {
		TurnSequence turnSequence = new TurnSequence(1);
		
		assertEquals("Sequence", 1, turnSequence.getTurnSequence());
	}

	@Test
	public void createTurnSequenceMessage() {
		String messageSequence = "00001";
		
		TurnSequence turnSequence = new TurnSequence(messageSequence, 0);
		
		assertEquals("Sequence", 1, turnSequence.getTurnSequence());
	}

	@Test
	public void buildTurnSequence() {
		TurnSequence turnSequence = new TurnSequence(1);
		
		assertEquals("Sequence build", "00001", turnSequence.build());
	}
}
