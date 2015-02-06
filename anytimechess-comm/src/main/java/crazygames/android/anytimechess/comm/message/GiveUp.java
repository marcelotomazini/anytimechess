package crazygames.android.anytimechess.comm.message;

import crazygames.android.anytimechess.comm.state.Header;


public class GiveUp implements Message {

	public static final String HEADER = Header.HEADER + "GIVEUP";
	private String destination;

	public GiveUp(String destination) {
		validateDestination(destination);
		this.destination = destination;
	}

	@Override
	public String build() {
		return HEADER;
	}

	@Override
	public String getDestination() {
		return destination;
	}
	
	private void validateDestination(String destination) {
		if (destination == null || destination.isEmpty())
			throw new RuntimeException("Invalid destination!");
	}
}
