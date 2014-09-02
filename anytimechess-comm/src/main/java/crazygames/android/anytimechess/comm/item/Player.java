package crazygames.android.anytimechess.comm.item;


public abstract class Player extends Item {

	private String player;
	
	protected Player(String player) {
		validate(player);
		this.player = player;
	}
	
	Player(String messageContext, int index) {
		String value = getValue(messageContext, index);
		process(value);
	}

	public String getPlayer() {
		return player;
	}

	@Override
	public int size() {
		return 12;
	}

	@Override
	public String build() {
		String format = "";
		for (int i = 0; i < size() - player.length(); i++)
			format += "0";
		
		return format + player; 
	}

	private void process(String value) {
		player = value.replaceFirst("0*", "");
	}

	private void validate(String player) {
		if(player == null || player.isEmpty() || player.length() > size())
			throw new RuntimeException("Invalid player identifier");
	}
}