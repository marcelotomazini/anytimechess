package crazygames.android.anytimechess.comm.message;


class TurnSequence extends Item {

	private int turnSequence;
	
	TurnSequence(int turnSequence) {
		this.turnSequence = turnSequence;
	}

	TurnSequence(String messageContext, int index) {
		String value = getValue(messageContext, index);
		process(value);
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

	int getTurnSequence() {
		return turnSequence;
	}

	private void process(String value) {
		turnSequence = Integer.parseInt(value);
	}
}
