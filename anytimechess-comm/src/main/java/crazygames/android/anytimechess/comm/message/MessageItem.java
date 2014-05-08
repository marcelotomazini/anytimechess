package crazygames.android.anytimechess.comm.message;

public abstract class MessageItem {
	
	protected abstract int size();
	
	protected abstract String build();
	
	protected String getValue(String messageContext, int index) {
		return messageContext.substring(index, index + size());
	}
}
