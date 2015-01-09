package crazygames.android.anytimechess.state;

import crazygames.android.anytimechess.comm.message.Message;
import crazygames.android.anytimechess.comm.message.State;

public class StateMessage implements Message {
	
	private String destination;
	private State state;

	public StateMessage(String destination, State state) {
		this.destination = destination;
		this.state = state;
	}

	@Override
	public String build() {
		return state.build();
	}

	@Override
	public String getDestination() {
		return destination;
	}
}
