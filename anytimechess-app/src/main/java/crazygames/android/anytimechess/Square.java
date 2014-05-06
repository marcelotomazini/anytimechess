package crazygames.android.anytimechess;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class Square extends FrameLayout {

	private final int row;
	private final char column;
	private final int originalBackgroundColor;

	public Square(final Context context, final int backgroundColor, final int row, final char column) {
		super(context);
		this.row = row;
		this.column = column;
		this.originalBackgroundColor = backgroundColor;
		setOriginalBackgroundColor();
		setLayoutParams(new AbsListView.LayoutParams(100, 100));

		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				final PieceView child = (PieceView) ((Square)v).getChildAt(0);
				if(child == null)
					return;

				final Piece piece = child.getPiece();

				Board parent = (Board)Square.this.getParent();
				for(int i = 0; i < parent.getChildCount(); i++) {
					final Square s = (Square)parent.getChildAt(i);
					s.setOriginalBackgroundColor();
				}

				final List<Move> moves = piece.moves(child.getColumn(), child.getRow(), BoardAdapter.game);
				for(final Move move : moves) {
					final MoveResponse moveResponse = piece.canMove(move, BoardAdapter.game, BoardAdapter.game.getBoard().get(move.getColTo(), move.getRowTo()));
					if(moveResponse.getMoveType().equals(Move.Type.CANTMOVE))
						continue;
					System.out.println(moveResponse.getMoveType());

					parent = (Board)Square.this.getParent();
					for(int i = 0; i < parent.getChildCount(); i++) {
						final Square s = (Square)parent.getChildAt(i);
						if(s.getRow() == move.getRowTo() && s.getColumn() == move.getColTo())
							s.setBackgroundColor(Color.CYAN);
					}

				}
				setBackgroundColor(Color.CYAN);
			}
		});
	}

	protected void setOriginalBackgroundColor() {
		setBackgroundColor(originalBackgroundColor);
	}

	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}

}
