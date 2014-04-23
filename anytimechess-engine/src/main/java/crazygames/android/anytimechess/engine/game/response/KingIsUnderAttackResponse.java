package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;



public class KingIsUnderAttackResponse extends MoveResponse {
	public KingIsUnderAttackResponse() {
		super(Move.Type.KINGISUNDERATTACK);
	}
}
