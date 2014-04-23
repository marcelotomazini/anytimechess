package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;


public class EnpassantResponse extends MoveResponse{
	private final Action action;
	private final Position pawn;
	public EnpassantResponse(final Action action, final Position pawn) {
		super(Move.Type.ENPASSANT);
		this.action = action;
		this.pawn = pawn;
	}

	public Action getAction() {
		return action;
	}

	public Position getPawn() {
		return pawn;
	}

	@Override
	public String toString() {
		return super.toString()+" ["+action+", -"+pawn+"]";
	}
}
