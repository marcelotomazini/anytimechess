package crazygames.android.anytimechess.comm.message;

public class VisitPlayer extends Player {

	public VisitPlayer(String visitPlayer) {
		super(visitPlayer);
	}

	VisitPlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	public String getVisitPlayer() {
		return getPlayer();
	}
}
