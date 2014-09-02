package crazygames.android.anytimechess.comm.message;

public class Header extends Item {
	
	public static final String HEADER = "atchess";
	
	public Header() {}

	@Override
	protected int size() {
		return HEADER.length();
	}

	@Override
	public String build() {
		return HEADER;
	}
}
