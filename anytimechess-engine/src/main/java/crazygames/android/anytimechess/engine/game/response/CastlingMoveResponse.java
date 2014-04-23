package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;



public class CastlingMoveResponse extends MoveResponse{

	private final Action kingAction;
	private final Action rookAction;

	public CastlingMoveResponse(final Action kingAction, final Action rookAction) {
		super(Move.Type.CASTLING);
		this.kingAction = kingAction;
		this.rookAction = rookAction;
	}

	public Action getKingAction() {
		return kingAction;
	}

	public Action getRookAction() {
		return rookAction;
	}

	@Override
	public String toString() {
		return super.toString()+" ["+kingAction+", "+rookAction+"]";
	}
}
