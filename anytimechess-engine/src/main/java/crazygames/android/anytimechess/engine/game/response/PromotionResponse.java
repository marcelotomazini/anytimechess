package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;



public class PromotionResponse extends MoveResponse {
	private Action action;
	private char piece;

	public PromotionResponse(final Action action) {
		super(Move.Type.PROMOTION);
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(final Action action) {
		this.action = action;
	}

	public char getPiece() {
		return piece;
	}

	public void setPiece(final char piece) {
		this.piece = piece;
	}

	@Override
	public String toString() {
		return super.toString() + " [" + action + piece + "]";
	}
}
