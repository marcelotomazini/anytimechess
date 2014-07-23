package crazygames.android.anytimechess.comm.message;

class Header extends MessageItem {
	
	private static final String HEADER = "atchess";
	
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
