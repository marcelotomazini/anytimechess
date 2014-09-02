package crazygames.android.anytimechess.engine.game;

import java.util.Stack;

import crazygames.android.anytimechess.engine.pieces.Bishop;
import crazygames.android.anytimechess.engine.pieces.King;
import crazygames.android.anytimechess.engine.pieces.Knight;
import crazygames.android.anytimechess.engine.pieces.Pawn;
import crazygames.android.anytimechess.engine.pieces.Piece;
import crazygames.android.anytimechess.engine.pieces.Queen;
import crazygames.android.anytimechess.engine.pieces.Rook;


public class Board {

	private Piece[][] map = new Piece[8][8];
	private King whiteKing;
	private King blackKing;
	private final Stack<Action> actionStack = new Stack<Action>();
	private boolean inTransaction = false;

	public Board() {
		for (int row = 1; row <= 8; row++)
			for (char col = 'a'; col <= 'h'; col++)
				set(col, row, initPiece(row, col));
	}

	public Board(Piece[][] map) {
		this.map = map;
	}

	public final static int colIndex(final char col) {
		return col - 'a';
	}

	public Piece get(final char col, final int row) {
		return get(colIndex(col), row - 1);
	}


	public Piece set(final char col, final int row, final Piece piece) {
		final Piece p = get(col, row);
		if (piece != null)
			if (piece.equals(whiteKing)) {
				whiteKing.setCol(col);
				whiteKing.setRow(row);
			}
			else if (piece.equals(blackKing)) {
				blackKing.setCol(col);
				blackKing.setRow(row);
			}
		if (inTransaction) {
			final Action action = new Action(p, col, row);
			actionStack.push(action);
		}

		map[row - 1][colIndex(col)] = piece;
		return p;
	}

	private Piece get(final int col, final int row) {
		return map[row][col];
	}

	public boolean isEmpty(final char col, final int row) {
		return get(col, row) == null;
	}

	private final Piece initPiece(final int row, final int col) {
		if (row > 2 && row < 7)
			return null;
		if (row == 2)
			return new Pawn().white();
		else if (row == 7)
			return new Pawn().black();
		Piece piece = null;
		switch (col) {
		case 'a':
		case 'h':
			piece = new Rook();
			break;
		case 'b':
		case 'g':
			piece = new Knight();
			break;
		case 'c':
		case 'f':
			piece = new Bishop();
			break;
		case 'd':
			piece = new Queen();
			break;
		case 'e':
			if (row == 1)
				piece = whiteKing = new King();
			else if (row == 8)
				piece = blackKing = new King();
			break;
		default:
			return null;

		}
		if (row == 1)
			return piece.white();
		if (row == 8)
			return piece.black();
		return null;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (int row = 8; row > 0; row--) {

			for (char col = 'a'; col <= 'h'; col++) {
				final Piece piece = get(col, row);
				sb.append(piece == null ? (col + row) % 2 == 1 ? '.' : '.' :
					piece.unicode());
			}
			sb.append('\n');
		}
		return sb.toString();
	}

	public void move(final Move move) {
		final Piece piece = get(move.getColFrom(), move.getRowFrom());
		piece.setMoved(true);
		set(move.getColTo(), move.getRowTo(), piece);
		set(move.getColFrom(), move.getRowFrom(), null);
	}

	public King getBlackKing() {
		return blackKing;
	}

	public King getWhiteKing() {
		return whiteKing;
	}

	public void start() {
		actionStack.empty();
		inTransaction = true;
	}

	public void rollback() {
		inTransaction = false;
		while (!actionStack.empty()) {
			final Action action = actionStack.pop();
			set(action.col, action.row, action.piece);
		}
	}

	public void commit() {
		actionStack.clear();
		inTransaction = false;
	}

	private static class Action {
		private final Piece piece;
		private final char col;
		private final int row;

		private Action(final Piece piece, final char col, final int row) {
			this.piece = piece;
			this.col = col;
			this.row = row;
		}
	}

	public Piece[][] getMap() {
		return map;
	}
}
