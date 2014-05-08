package crazygames.android.anytimechess.comm.message;


public class HomePlayer extends Player {

	public HomePlayer(String homePlayer) {
		super(homePlayer);
	}

	HomePlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	public String getHomePlayer() {
		return getPlayer();
	}
}
