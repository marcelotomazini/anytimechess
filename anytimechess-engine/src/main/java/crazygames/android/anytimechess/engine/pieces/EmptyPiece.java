package crazygames.android.anytimechess.engine.pieces;

import java.util.List;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;


public class EmptyPiece extends Piece {
	@Override
	protected char _code() {
		return 0;
	}

	@Override
	public MoveResponse canMove(final Move move, final Game game, final Piece pieceTo) {
		return null;
	}

	@Override
	public List<Move> moves(final char col, final int row, final Game game) {
		return null;
	}
	
	@Override
	public Color color() {
		return Color.WHITE;
	}
}
