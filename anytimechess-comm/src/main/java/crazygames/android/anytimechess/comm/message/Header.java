package crazygames.android.anytimechess.comm.message;

public class Header extends MessageItem {
	
	public static final String HEADER = "atchess";
	
	Header() {}

	@Override
	protected int size() {
		return HEADER.length();
	}

	@Override
	protected String build() {
		return HEADER;
	}

	String getHeaderValue() {
		return HEADER;
	}
}
