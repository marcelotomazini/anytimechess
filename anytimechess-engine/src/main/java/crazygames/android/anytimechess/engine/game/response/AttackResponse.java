package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;


public class AttackResponse extends MoveResponse {
	private final Action action;
	public AttackResponse(final Action action) {
		super(Move.Type.ATTACK);
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	@Override
	public String toString() {
		return super.toString()+" ["+action+"]";
	}
}
