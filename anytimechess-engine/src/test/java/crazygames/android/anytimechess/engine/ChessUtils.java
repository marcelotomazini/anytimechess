package crazygames.android.anytimechess.engine;

import java.util.List;

import crazygames.android.anytimechess.engine.game.Game;

public class ChessUtils {

	public static void play(final Game game, final List<Turn> moves) {
		for (final Turn turn : moves)
			game.move(turn.cf, turn.rf, turn.ct, turn.rt, turn.p);
	}

	static class Turn {

		private final char cf;
		private final int rf;
		private final char ct;
		private final int rt;
		private char p;

		public Turn(final char cf, final int rf, final char ct, final int rt) {
			this.cf = cf;
			this.rf = rf;
			this.ct = ct;
			this.rt = rt;
		}

		public Turn(final char cf, final int rf, final char ct, final int rt, final char p) {
			this(cf, rf, ct, rt);
			this.p = p;
		}

	}

}
