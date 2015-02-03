package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.widget.ImageView;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class PieceView extends ImageView {

	private final Piece piece;

	public PieceView(final Context context, final Piece piece) {
		super(context);
		this.piece = piece;
		
		String imageName = "white_0";
		if(piece != null)
			imageName = piece.color().name().toLowerCase() + "_" + String.valueOf(piece.code()).toLowerCase();
		
		final int identifier = getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		setImageResource(identifier);
	}

	@Override
	public String toString() {
		if(piece == null)
			return "null piece";
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
