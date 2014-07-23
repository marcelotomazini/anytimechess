package crazygames.android.anytimechess.comm.message;


class HomePlayer extends Player {

	HomePlayer(String homePlayer) {
		super(homePlayer);
	}

	HomePlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	String getHomePlayer() {
		return getPlayer();
	}
}
