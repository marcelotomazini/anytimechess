package crazygames.android.anytimechess.comm.state;

public class Header extends Item {
	
	public static final String HEADER = "atchess";
	
	@Override
	public int size() {
		return HEADER.length();
	}

	@Override
	public String build() {
		return HEADER;
	}
}
