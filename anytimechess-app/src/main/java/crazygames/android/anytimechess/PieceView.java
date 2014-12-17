package crazygames.android.anytimechess;

import android.content.Context;
import android.widget.ImageView;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class PieceView extends ImageView {

	private final Piece piece;

	public PieceView(final Context context, final Piece piece) {
		super(context);
		this.piece = piece;
		
		final String imageName = piece.color().name().toLowerCase() + "_" + String.valueOf(piece.code()).toLowerCase();
		final int identifier = getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		setImageResource(identifier);
	}

	@Override
	public String toString() {
		return getPiece().toString();
	}

	public Piece getPiece() {
		return piece;
	}

	public int getRow() {
		return ((Square)getParent()).getRow();
	}

	public char getColumn() {
		return ((Square)getParent()).getColumn();
	}
}
