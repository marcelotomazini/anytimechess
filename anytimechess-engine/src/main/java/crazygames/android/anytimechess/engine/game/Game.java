package crazygames.android.anytimechess.engine.game;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;

import java.util.LinkedList;
import java.util.List;

import crazygames.android.anytimechess.engine.game.response.CantMoveResponse;
import crazygames.android.anytimechess.engine.game.response.CastlingMoveResponse;
import crazygames.android.anytimechess.engine.game.response.EnpassantResponse;
import crazygames.android.anytimechess.engine.game.response.KingIsUnderAttackResponse;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.game.response.PromotionResponse;
import crazygames.android.anytimechess.engine.pieces.Bishop;
import crazygames.android.anytimechess.engine.pieces.King;
import crazygames.android.anytimechess.engine.pieces.Knight;
import crazygames.android.anytimechess.engine.pieces.Piece;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
import crazygames.android.anytimechess.engine.pieces.Queen;
import crazygames.android.anytimechess.engine.pieces.Rook;



public class Game {

	private final Board board;
	private final List<Move> moves;
	private Piece.Color turn;


	public Game() {
		board = new Board();
		moves = new LinkedList<Move>();
		turn = Piece.Color.WHITE;
	}
	
	public Game(Color turn, Piece[][] piece) {
		board = new Board(piece);
		moves = new LinkedList<Move>();
		this.turn = turn;
	}

	public Board getBoard() {
		return board;
	}

	public List<Move> getMoves() {
		return moves;
	}

	public final MoveResponse move(final char colFrom, final int rowFrom,
			final char colTo, final int rowTo) {
		return move(colFrom, rowFrom, colTo, rowTo, null);
	}

	public final MoveResponse move(final char colFrom, final int rowFrom,
			final char colTo, final int rowTo,
			final Character promotionPiece) {

		if (colFrom < 'a' || colFrom > 'h' || rowFrom < 1 || rowFrom > 8 ||
				colTo < 'a' || colTo > 'h' || rowTo < 1 || rowTo > 8 ||
				colFrom == colTo && rowFrom == rowTo)
			return new CantMoveResponse();

		final Piece pieceFrom = board.get(colFrom, rowFrom);

		if (pieceFrom == null)
			return new CantMoveResponse();

		if (!pieceFrom.color().equals(turn))
			return new CantMoveResponse();

		final Piece pieceTo = board.get(colTo, rowTo);

		if (pieceTo != null && pieceFrom.color().equals(pieceTo.color()))
			return new CantMoveResponse();


		final Move move = new Move(pieceFrom.code(), colFrom, rowFrom, colTo, rowTo);

		final MoveResponse moveResponse = pieceFrom.canMove(move, this, pieceTo);
		final Move.Type moveType = moveResponse.getMoveType();


		if (moveType.equals(Move.Type.CANTMOVE))
			return moveResponse;

		board.start();
		doTurn(move, moveResponse, promotionPiece);

		final King king = turn.equals(Piece.Color.WHITE) ?
				board.getWhiteKing() : board.getBlackKing();
				if (Piece.underAttack(king.color(), king.getCol(),
						king.getRow(), this)) {
					board.rollback();
					return new KingIsUnderAttackResponse();
				}

				board.commit();
				moves.add(move);

				final boolean check = isCheck();
				moveResponse.check(check);
				if (check)
					moveResponse.checkmate(isCheckmate());
				nextTurn();

				return moveResponse;
	}

	private void doTurn(final Move move, final MoveResponse response,
			final Character promotionPiece) {
		switch (response.getMoveType()) {
		case MOVENMENT:
		case ATTACK:
			applyMove(move);
			break;
		case CASTLING:
			applyMove(move);
			final CastlingMoveResponse castling = (CastlingMoveResponse) response;
			castling(castling.getRookAction().getFrom().getCol(),
					castling.getRookAction().getFrom().getRow(),
					castling.getRookAction().getTo().getCol(),
					castling.getRookAction().getTo().getRow());
			break;
		case ENPASSANT:
			applyMove(move);
			final EnpassantResponse enpassant = (EnpassantResponse) response;
			enpassant(enpassant.getPawn().getCol(), enpassant.getPawn().getRow());
			break;
		case PROMOTION:
			applyMove(move);
			final PromotionResponse promotion = (PromotionResponse) response;
			promotion.setPiece(promotionPiece);
			promotion(move.getColTo(), move.getRowTo(), promotionPiece);
			break;
		default:
			break;
		}
	}

	private void promotion(final char colTo, final int rowTo, final Character promotionPiece) {
		Piece promotion;
		if (promotionPiece != null)
			switch (Character.toLowerCase(promotionPiece)) {
			case 'n':
				promotion = new Knight().color(turn);
				break;
			case 'b':
				promotion = new Bishop().color(turn);
				break;
			case 'r':
				promotion = new Rook().color(turn);
				break;
			case 'q':
			default:
				promotion = new Queen().color(turn);
				break;
			}
		else
			promotion = new Queen().color(turn);

		board.set(colTo, rowTo, promotion);
	}

	private void castling(final char rookColFrom,
			final int rookRowFrom, final char rookColTo, final int rookRowTo) {
		board.move(new Move('?', rookColFrom, rookRowFrom,
				rookColTo, rookRowTo));
	}

	private final void applyMove(final Move move) {
		board.move(move);
	}

	private void enpassant(final char col, final int row) {
		board.set(col, row, null);
	}

	private final void nextTurn() {
		if (turn.equals(Piece.Color.WHITE))
			turn = Piece.Color.BLACK;
		else
			turn = Piece.Color.WHITE;
	}

	public Move getLastMove() {
		if(moves.isEmpty())
			return null;

		return moves.get(moves.size() - 1);
	}
	
	private boolean isCheck() {
		return isCheck(turn);
	}

	public boolean isCheck(Color myColor) {
		final King king = myColor == WHITE ? board.getWhiteKing() : board.getBlackKing();
		if (Piece.underAttack(king.color(), king.getCol(), king.getRow(), this)) {
			board.rollback();
			return true;
		}
		return false;
	}

	private boolean isCheckmate() {
		return isCheckmate(turn);
	}
	
	public boolean isCheckmate(Color myColor) {
		final King king = myColor == WHITE ? board.getWhiteKing() : board.getBlackKing();

		final Piece.Color color = king.color();
		for (char col = 'a'; col <= 'h'; col++)
			for (int row = 1; row <= 8; row++) {
				final Piece piece = board.get(col, row);
				if (piece != null && piece.color().equals(color)) {
					final List<Move> moves = piece.moves(col, row, this);
					for (final Move move : moves) {
						final MoveResponse response = piece.canMove(move, this,
								board.get(move.getColTo(), move.getRowTo()));
						board.start();
						doTurn(move, response, null);

						if (!Piece.underAttack(king.color(), king.getCol(), king.getRow(), this)) {
							board.rollback();
							return false;
						}

						board.rollback();
					}
				}
			}
		return true;
	}

	public Piece.Color getTurn() {
		return turn;
	}
}
