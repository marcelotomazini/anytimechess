package crazygames.android.anytimechess.comm.state;

public abstract class Item {
	
	@Override
	public String toString() {
		return build();
	}
	
	public abstract int size();
	
	public abstract String build();
	
	protected String getValue(String messageContext, int index) {
		return messageContext.substring(index, index + size());
	}
}
