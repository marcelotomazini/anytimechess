package crazygames.android.anytimechess;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.game.response.Position;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class Board extends GridView {

	private final Game game = new Game();

	private final Context context;

	private final List<PieceView> pieces = new ArrayList<PieceView>();
	private PieceView selectedPiece;

	public Board(final Context context) {
		super(context);
		this.context = context;

		setBackgroundColor(Color.WHITE);
		setNumColumns(8);
		setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		setGravity(Gravity.CENTER_HORIZONTAL);

		createPieces();
		createSquares();
		refresh();
	}

	private void createPieces() {
		for (int row = 1; row <= 8; row++)
			for (char col = 'a'; col <= 'h'; col++) {
				final Piece piece = game.getBoard().get(col, row);
				if(piece != null) 
					getPieces().add(new PieceView(context, piece));
			}
	}

	private void refresh() {
		for(char col = 'a'; col <= 'h'; col++)
			for(int row = 1; row <= 8; row++) {
				final Piece piece = game.getBoard().get(col, row);
				if(piece != null)
					for(final PieceView pieceView : getPieces())
						if(piece.equals(pieceView.getPiece()))
							for(int i = 0; i < getAdapter().getCount(); i++) {
								final Square item = (Square) getAdapter().getItem(i);
								if(item.getPosition().equals(new Position(col, row))) {
									final Square parent = (Square) pieceView.getParent();
									if(parent != null)
										parent.removeAllViews();
									item.addView(pieceView);
								}
							}
			}
	}

	private void createSquares() {
		setAdapter(new BoardAdapter(context));

		for(int i = 0; i < getAdapter().getCount(); i++) {
			final Square item = (Square) getAdapter().getItem(i);
			item.setOnClickListener(new OnClickListener() {
				@Override public void onClick(final View v) {
					if(hasSelectedPiece() /*&& ((Square)v).getChildAt(0) == null*/)
						move((Square) v);

					final PieceView child = (PieceView) ((Square)v).getChildAt(0);
					setSelectedPiece(child);

					if(child == null) {
						setBackgroundOriginalColor();
						return;
					}

					final Piece piece = child.getPiece();
					if(!game.getTurn().equals(piece.color())) {
						setBackgroundOriginalColor();
						return;
					}


					setBackgroundOriginalColor();

					final List<Move> moves = piece.moves(child.getColumn(), child.getRow(), game);
					for(final Move move : moves) {
						final MoveResponse moveResponse = piece.canMove(move, game, game.getBoard().get(move.getColTo(), move.getRowTo()));
						if(moveResponse.getMoveType().equals(Move.Type.CANTMOVE))
							continue;
						System.out.println(moveResponse.getMoveType());

						for(int i = 0; i < getChildCount(); i++) {
							final Square s = (Square)getChildAt(i);
							if(s.getRow() == move.getRowTo() && s.getColumn() == move.getColTo())
								s.setBackgroundColor(Color.CYAN);
						}

					}
					setBackgroundColor(Color.CYAN);
				}

			});
		}
	}

	private void setBackgroundOriginalColor() {
		for(int i = 0; i < getChildCount(); i++) {
			final Square s = (Square)getChildAt(i);
			s.setOriginalBackgroundColor();
		}
	}

	private void move(final Square square) {
		final PieceView selectedPiece = getSelectedPiece();
		game.move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
		refresh();
	}


	public PieceView getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(final PieceView selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	public boolean hasSelectedPiece() {
		return getSelectedPiece() != null;
	}

	public List<PieceView> getPieces() {
		return pieces;
	}

}
