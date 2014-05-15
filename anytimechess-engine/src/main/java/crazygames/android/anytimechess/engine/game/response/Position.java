package crazygames.android.anytimechess.engine.game.response;

public class Position {
	private final char col;
	private final int row;

	public Position(final char col, final int row) {
		this.col = col;
		this.row = row;
	}

	public char getCol() {
		return col;
	}

	public int getRow() {
		return row;
	}

	@Override
	public String toString() {
		return col + "" + row;
	}

	@Override
	public boolean equals(final Object obj) {
		if(obj == null)
			return false;

		final Position another = (Position) obj;
		return col == another.col && row == another.row;
	}
}