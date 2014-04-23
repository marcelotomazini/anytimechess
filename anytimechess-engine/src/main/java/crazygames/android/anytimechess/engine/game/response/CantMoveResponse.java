package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;



public class CantMoveResponse extends MoveResponse {
	public CantMoveResponse() {
		super(Move.Type.CANTMOVE);
	}
}
