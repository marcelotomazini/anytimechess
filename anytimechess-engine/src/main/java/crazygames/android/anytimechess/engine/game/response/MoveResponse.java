package crazygames.android.anytimechess.engine.game.response;


import crazygames.android.anytimechess.engine.game.Move;


public class MoveResponse {

	protected Move.Type type;
	protected boolean check = false;
	protected boolean checkmate = false;

	public MoveResponse(final Move.Type type) {
		this.type = type;
	}

	public Move.Type getMoveType() {
		return type;
	}

	@Override
	public String toString() {
		return type.toString() + (checkmate?" (CHECKMATE)":check?" (CHECK)":"");
	}

	public MoveResponse check(final boolean check){
		this.check = check;
		return this;
	}

	public boolean isCheck() {
		return check;
	}

	public MoveResponse checkmate(final boolean checkmate) {
		this.checkmate = checkmate;
		return this;
	}

	public boolean isCheckmate() {
		return checkmate;
	}
}
