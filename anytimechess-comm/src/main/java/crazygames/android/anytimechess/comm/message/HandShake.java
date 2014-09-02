package crazygames.android.anytimechess.comm.message;


public abstract class HandShake implements Message {

	private Header header;
	private String destination;

	public HandShake(String destination) {
		validateDestination(destination);
		
		header = new Header();
		this.destination = destination;
	}

	@Override
	public String build() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(header.build());
		buffer.append(" ");
		buffer.append(buildMessage());
		
		return buffer.toString();
	}

	@Override
	public String getDestination() {
		return destination;
	}
	
	protected abstract String buildMessage();
	
	private void validateDestination(String destination) {
		if (destination == null || destination.isEmpty())
			throw new RuntimeException("Invalid destination!");
	}
}
