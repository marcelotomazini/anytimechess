package crazygames.android.anytimechess.comm.message;


public class TurnSequence extends MessageItem {

	private int turnSequence;
	
	TurnSequence(String messageContext, int index) {
		String value = getValue(messageContext, index);
		process(value);
	}

	public TurnSequence(int turnSequence) {
		this.turnSequence = turnSequence;
	}

	public int getTurnSequence() {
		return turnSequence;
	}

	@Override
	protected int size() {
		return 5;
	}

	@Override
	protected String build() {
		String format = "%0" + size() + "d";		
		return String.format(format, turnSequence); 
	}

	private void process(String value) {
		turnSequence = Integer.parseInt(value);
	}

}
