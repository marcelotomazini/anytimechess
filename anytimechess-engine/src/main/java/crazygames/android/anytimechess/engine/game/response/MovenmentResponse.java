package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;



public class MovenmentResponse extends MoveResponse {
	private final Action action;
	public MovenmentResponse(final Action action) {
		super(Move.Type.MOVENMENT);
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
