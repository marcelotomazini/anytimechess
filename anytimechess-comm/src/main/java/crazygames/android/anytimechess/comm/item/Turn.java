package crazygames.android.anytimechess.comm.item;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.BLACK;
import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;

public class Turn extends Item {

	private Color turn;
	
	public Turn(Color turn) {
		validate(turn);
		this.turn = turn;
	}

	public Turn(String messageContext, int index) {
		turn = convert(getValue(messageContext, index));
	}

	public Color getTurnValue() {
		return turn;
	}

	@Override
	public String build() {
		return turn.toString();
	}

	@Override
	public int size() {
		return 5;
	}

	private void validate(Color turn) {
		if(turn == null)
			throw new RuntimeException("Invalid turn code");
	}

	private Color convert(String value) {
		if (value.equals(WHITE.toString()))
			return WHITE;
		return BLACK;
	}

	public Color getReverse() {
		return turn == WHITE ? BLACK : WHITE;
	}
}
