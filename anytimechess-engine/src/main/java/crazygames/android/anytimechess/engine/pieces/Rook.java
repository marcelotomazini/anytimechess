package crazygames.android.anytimechess.engine.pieces;

import java.util.LinkedList;
import java.util.List;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.Action;
import crazygames.android.anytimechess.engine.game.response.AttackResponse;
import crazygames.android.anytimechess.engine.game.response.CantMoveResponse;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.game.response.MovenmentResponse;

public class Rook extends Piece {


	@Override
	protected char _code() {
		return 'r';
	}

	@Override
	public MoveResponse canMove(final Move move, final Game game, final Piece pieceTo) {

		if(pieceTo != null && color().equals(pieceTo.color()))
			return new CantMoveResponse();

		final int drow = move.getRowTo() - move.getRowFrom();
		final int dcol = move.getColTo() - move.getColFrom();
		if (drow * dcol != 0)
			return new CantMoveResponse();
		if (drow != 0) {
			final int rStep = drow < 0 ? -1 : 1;
			for (int i = 1; i < Math.abs(drow); i++)
				if (!game.getBoard().isEmpty(move.getColFrom(),
						move.getRowFrom() + i * rStep))
					return new CantMoveResponse();
		}
		else if (dcol != 0) {
			final int cStep = dcol < 0 ? -1 : 1;
			for (int i = 1; i < Math.abs(dcol); i++)
				if (!game.getBoard().isEmpty((char) (move.getColFrom() + i * cStep),
						move.getRowFrom()))
					return new CantMoveResponse();
		}
		if (pieceTo == null)
			return new MovenmentResponse(new Action(move));
		else
			return new AttackResponse(new Action(move));
	}

	@Override
	public List<Move> moves(final char col, final int row, final Game game) {
		final List<Move> moves = new LinkedList<Move>();
		final char pieceCode = _code();


		for (char c = 'a'; c <= 'h'; c++) {
			if (c == col)
				continue;
			moves.add(new Move(pieceCode, col, row, c, row));
		}

		for (int r = 1; r <= 8; r++) {
			if (r == row)
				continue;
			moves.add(new Move(pieceCode, col, row, col, r));
		}

		return moves;
	}
}
