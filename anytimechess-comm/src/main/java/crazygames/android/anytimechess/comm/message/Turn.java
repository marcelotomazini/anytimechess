package crazygames.android.anytimechess.comm.message;

class Turn extends Item {

	//TODO Pilo convert to enum
	private static final String CODE_HOME = "H";
	private static final String CODE_VISIT = "V";
	private String turnCode;
	
	Turn(String turnCode) {
		validate(turnCode);
		this.turnCode = turnCode;
	}

	Turn(String messageContext, int index) {
		turnCode = getValue(messageContext, index);
	}

	@Override
	protected String build() {
		return turnCode;
	}

	@Override
	protected int size() {
		return 1;
	}

	boolean isHomeTurn() {
		return CODE_HOME.equals(turnCode);
	}

	boolean isVisitTurn() {
		return !isHomeTurn();
	}

	private void validate(String turnCode) {
		if(turnCode == null || turnCode.isEmpty() || turnCode.length() > size() || !isValidCode(turnCode))
			throw new RuntimeException("Invalid turn code");
	}
	
	private boolean isValidCode(String turnCode) {
		return turnCode.equals(CODE_HOME) || turnCode.equals(CODE_VISIT);
	}
}
