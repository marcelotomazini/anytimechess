package crazygames.android.anytimechess.comm.state;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

import crazygames.android.anytimechess.comm.state.GameState;
import crazygames.android.anytimechess.engine.game.Game;


public class GameStateTest {

	private static String VALID_GAMESTATE = "RNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	
	@Test
	public void buildGameState() {
		GameState gameState = new GameState(new Game());
		String gameStateMessage = gameState.build();
		
		assertEquals("Size of gameStateMessage", 68, gameStateMessage.length());
		assertEquals("GameState build message", VALID_GAMESTATE, gameStateMessage);
	}

	@Test
	public void createGameStateReceived() {
		GameState gameState = new GameState(VALID_GAMESTATE, 0);
		String gameStateMessage = gameState.build();
		
		assertEquals("Size of gameStateMessage", 68, gameStateMessage.length());
		assertEquals("GameState build message", VALID_GAMESTATE, gameStateMessage);
	}
	

}
