package crazygames.android.anytimechess.comm.message;

import crazygames.android.anytimechess.comm.state.Header;


public abstract class HandShake implements Message {

	public static final String HEADER = Header.HEADER + "HS";
	private String destination;

	public HandShake(String destination) {
		validateDestination(destination);
		this.destination = destination;
	}

	@Override
	public String build() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(HEADER);
		buffer.append(handShakeType());
		buffer.append(" ");
		buffer.append(buildMessage());
		
		return buffer.toString();
	}

	@Override
	public String getDestination() {
		return destination;
	}
	
	protected abstract String buildMessage();
	
	protected abstract String handShakeType();
	
	private void validateDestination(String destination) {
		if (destination == null || destination.isEmpty())
			throw new RuntimeException("Invalid destination!");
	}
}
