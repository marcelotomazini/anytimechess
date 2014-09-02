package crazygames.android.anytimechess.comm.message;


class InviteContent extends Item {
	
	public static final String MESSAGE = "Hey you! % is inviting you to play AnytimeChess!";
	
	private String player;
	
	InviteContent() {}
	
	InviteContent(String player) {
		this.player = player;
	}

	@Override
	protected int size() {
		return MESSAGE.length();
	}

	@Override
	protected String build() {
		return String.format(MESSAGE, player);
	}
}
