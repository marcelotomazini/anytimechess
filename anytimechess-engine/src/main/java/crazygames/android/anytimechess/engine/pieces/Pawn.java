package crazygames.android.anytimechess.engine.pieces;

import java.util.LinkedList;
import java.util.List;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.Action;
import crazygames.android.anytimechess.engine.game.response.AttackResponse;
import crazygames.android.anytimechess.engine.game.response.CantMoveResponse;
import crazygames.android.anytimechess.engine.game.response.EnpassantResponse;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.game.response.MovenmentResponse;
import crazygames.android.anytimechess.engine.game.response.Position;
import crazygames.android.anytimechess.engine.game.response.PromotionResponse;


public class Pawn extends Piece {

	@Override
	protected char _code() {
		return 'p';
	}

	@Override
	public MoveResponse canMove(final Move move, final Game game, final Piece pieceTo) {

		if(pieceTo != null && color().equals(pieceTo.color()))
			return new CantMoveResponse();

		//todo check king is under attack after move
		if (move.getColFrom() == move.getColTo()) {
			if (pieceTo != null)
				return new CantMoveResponse();
			if (color().equals(Color.WHITE)) {
				if (move.getRowTo() < move.getRowFrom())
					return new CantMoveResponse();
				if (move.getRowFrom() == 2) {
					if (move.getRowTo() - move.getRowFrom() > 2)
						return new CantMoveResponse();
					if (!game.getBoard().isEmpty(move.getColFrom(),
							move.getRowFrom() + 1))
						return new CantMoveResponse();
				}
				else if (move.getRowTo() - move.getRowFrom() != 1)
					return new CantMoveResponse();
				if (move.getRowTo() == 8)
					return new PromotionResponse(new Action(move));

			}
			else {
				if (move.getRowTo() > move.getRowFrom())
					return new CantMoveResponse();
				if (move.getRowFrom() == 7) {
					if (move.getRowFrom() - move.getRowTo() > 2)
						return new CantMoveResponse();
					if (!game.getBoard().isEmpty(move.getColFrom(),
							move.getRowFrom() - 1))
						return new CantMoveResponse();
				}
				else if (move.getRowFrom() - move.getRowTo() != 1)
					return new CantMoveResponse();
				if (move.getRowTo() == 1)
					return new PromotionResponse(new Action(move));

			}
			return new MovenmentResponse(new Action(move));
		}
		else if (Math.abs(move.getColFrom() - move.getColTo()) == 1) {

			if (color().equals(Color.WHITE)) {
				if (move.getRowTo() - move.getRowFrom() != 1)
					return new CantMoveResponse();
				if (move.getRowTo() == 8)
					return new PromotionResponse(new Action(move));
			}
			else {
				if (move.getRowTo() - move.getRowFrom() != -1)
					return new CantMoveResponse();
				if (move.getRowTo() == 1)
					return new PromotionResponse(new Action(move));
			}

			if (pieceTo == null) {
				final Move lastMove = game.getLastMove();
				if(lastMove == null)
					return new CantMoveResponse();

				if (Character.toLowerCase(lastMove.getPieceCode()) ==
						Character.toLowerCase(code()) &&
						lastMove.getColTo() == move.getColTo() &&
						Math.abs(lastMove.getRowFrom() -
								lastMove.getRowTo()) == 2 &&
								lastMove.getRowTo() == move.getRowFrom())
					return new EnpassantResponse(new Action(move), new Position(lastMove.getColTo(), lastMove.getRowTo()));
				else
					return new CantMoveResponse();
			} else
				return new AttackResponse(new Action(move));
		} else
			return new CantMoveResponse();


	}

	@Override
	public List<Move> moves(final char col, final int row, final Game game) {
		final List<Move> moves = new LinkedList<Move>();
		final char pieceCode = _code();

		if (color().equals(Color.WHITE)) {
			if (row == 2)
				moves.add(new Move(pieceCode, col, row, col, row + 2));
			if (row < 8) {
				moves.add(new Move(pieceCode, col, row, col, row + 1));
				if (col > 'a')
					moves.add(new Move(pieceCode, col, row, (char) (col - 1), row + 1));
				if (col < 'h')
					moves.add(new Move(pieceCode, col, row, (char) (col + 1), row + 1));
			}
		}
		else if (color().equals(Color.BLACK)) {
			if (row == 7)
				moves.add(new Move(pieceCode, col, row, col, row - 2));
			if (row < 8) {
				moves.add(new Move(pieceCode, col, row, col, row - 1));
				if (col > 'a')
					moves.add(new Move(pieceCode, col, row, (char) (col - 1), row - 1));
				if (col < 'h')
					moves.add(new Move(pieceCode, col, row, (char) (col + 1), row - 1));
			}
		}

		return moves;
	}
}
