package crazygames.android.anytimechess.comm.message;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.BLACK;
import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;

class Turn extends Item {

	private Color turn;
	
	Turn(Color turn) {
		validate(turn);
		this.turn = turn;
	}

	Turn(String messageContext, int index) {
		turn = convert(getValue(messageContext, index));
	}

	public Color getTurn() {
		return turn;
	}

	@Override
	protected String build() {
		return turn.toString();
	}

	@Override
	protected int size() {
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

	public Color invert() {
		return turn == WHITE ? BLACK : WHITE;
	}
}
