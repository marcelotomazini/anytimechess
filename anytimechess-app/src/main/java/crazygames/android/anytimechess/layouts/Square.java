package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TableRow;
import crazygames.android.anytimechess.engine.game.response.Position;

public class Square extends FrameLayout {

	private final Position position;
	private final int row;
	private final char column;
	private final int originalBackgroundColor;

	public Square(final Context context, final int backgroundColor, final int row, final char column) {
		super(context);
		this.row = row;
		this.column = column;
		this.position = new Position(column, row);
		this.originalBackgroundColor = backgroundColor;
		setOriginalBackgroundColor();
		setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 100, 1));
	}

	public void setOriginalBackgroundColor() {
		setBackgroundColor(originalBackgroundColor);
	}

	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}

	public Position getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return String.format("%s%s", column, row);
	}

}
