package crazygames.android.anytimechess.engine.pieces;

import java.util.List;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;

public abstract class Piece {

	protected Color color;
	protected boolean moved = false;

	public static boolean underAttack(final Color color, final char colTo, final int rowTo, final Game game) {
		for (int row = 1; row <= 8; row++)
			for (char col = 'a'; col <= 'h'; col++) {
				final Piece piece = game.getBoard().get(col, row);
				if (piece != null && !piece.color().equals(color)) {
					final Move move = new Move(piece.code(), col, row, colTo, rowTo);
					final Move.Type moveType = piece.canMove(move, game, new EmptyPiece()).getMoveType();
					if (moveType.equals(Move.Type.ATTACK) ||
							moveType.equals(Move.Type.MOVENMENT) ||
							moveType.equals(Move.Type.PROMOTION) ||
							moveType.equals(Move.Type.ENPASSANT))
						return true;
				}
			}
		return false;
	}

	public boolean isMoved() {
		return moved;
	}

	public void setMoved(final boolean moved) {
		this.moved = moved;
	}

	public final Piece color(final Color color) {
		this.color = color;
		return this;
	}

	public Color color() {
		return color;
	}

	public final Piece white() {
		return color(Color.WHITE);
	}

	public final Piece black() {
		return color(Color.BLACK);
	}

	protected abstract char _code();

	public char unicode() {
		switch (code()) {
		case 'K':
			return '\u2654';
		case 'Q':
			return '\u2655';
		case 'R':
			return '\u2656';
		case 'B':
			return '\u2657';
		case 'N':
			return '\u2658';
		case 'P':
			return '\u2659';

		case 'k':
			return '\u265A';
		case 'q':
			return '\u265B';
		case 'r':
			return '\u265C';
		case 'b':
			return '\u265D';
		case 'n':
			return '\u265E';
		case 'p':
			return '\u265F';
		}
		return '?';
	}

	public abstract MoveResponse canMove(Move move, Game game, Piece pieceTo);

	public char code() {
		return color.equals(Color.WHITE) ?
				Character.toUpperCase(_code()) : _code();
	}

	public static enum Color {
		WHITE, BLACK
	}

	public abstract List<Move> moves(char col, int row, Game game);

	@Override
	public String toString() {
		return String.format("%s %s", color(), code());
	}
}
