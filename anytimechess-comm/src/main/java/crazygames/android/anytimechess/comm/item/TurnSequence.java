package crazygames.android.anytimechess.comm.item;


public class TurnSequence extends Item {

	private int turnSequence;
	
	public TurnSequence(int turnSequence) {
		this.turnSequence = turnSequence;
	}

	public TurnSequence(String messageContext, int index) {
		String value = getValue(messageContext, index);
		process(value);
	}

	@Override
	public int size() {
		return 5;
	}

	@Override
	public String build() {
		String format = "%0" + size() + "d";		
		return String.format(format, turnSequence); 
	}

	public int getTurnSequence() {
		return turnSequence;
	}

	private void process(String value) {
		turnSequence = Integer.parseInt(value);
	}
}
