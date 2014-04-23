package crazygames.android.anytimechess.engine;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import crazygames.android.anytimechess.engine.ChessUtils.Turn;
import crazygames.android.anytimechess.engine.game.Game;

public class CheckMateTest {

	@Test
	public void checkMate() {
		final List<Turn> moves = Arrays.asList(
				new Turn('e', 2, 'e', 4),
				new Turn('a', 7, 'a', 5),
				new Turn('e', 4, 'e', 5),
				new Turn('d', 7, 'd', 5),
				new Turn('e', 5, 'd', 6),
				new Turn('g', 8, 'h', 6),
				new Turn('d', 1, 'h', 5),
				new Turn('a', 5, 'a', 4),
				new Turn('f', 1, 'd', 3),
				new Turn('a', 4, 'a', 3),
				new Turn('g', 1, 'h', 3),
				new Turn('a', 3, 'b', 2),
				new Turn('e', 1, 'g', 1),
				new Turn('b', 2, 'a', 1, 'n'),
				new Turn('a', 2, 'a', 3),
				new Turn('a', 1, 'b', 1),
				new Turn('c', 2, 'c', 3),
				new Turn('e', 7, 'e', 6),
				new Turn('c', 3, 'c', 4),
				new Turn('f', 8, 'e', 7),
				new Turn('d', 6, 'd', 7),
				new Turn('c', 8, 'd', 7),
				new Turn('d', 3, 'c', 4),
				new Turn('e', 6, 'e', 5),
				new Turn('a', 3, 'a', 4),
				new Turn('h', 6, 'g', 4),
				new Turn('h', 5, 'f', 7));

		final Game game = new Game();
		ChessUtils.play(game, moves);

		//assertions
	}
}
