package crazygames.android.anytimechess.comm.item;


public class HomePlayer extends Player {

	public HomePlayer(String homePlayer) {
		super(homePlayer);
	}

	public HomePlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	String getHomePlayer() {
		return getPlayer();
	}
}
