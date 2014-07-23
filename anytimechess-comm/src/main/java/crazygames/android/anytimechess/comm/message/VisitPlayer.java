package crazygames.android.anytimechess.comm.message;

class VisitPlayer extends Player {

	VisitPlayer(String visitPlayer) {
		super(visitPlayer);
	}

	VisitPlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	String getVisitPlayer() {
		return getPlayer();
	}
}
