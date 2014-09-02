package crazygames.android.anytimechess.comm.message;

public abstract class Item {
	
	@Override
	public String toString() {
		return build();
	}
	
	protected abstract int size();
	
	protected abstract String build();
	
	protected String getValue(String messageContext, int index) {
		return messageContext.substring(index, index + size());
	}
}
