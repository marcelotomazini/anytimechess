package crazygames.android.anytimechess.comm.item;

public class VisitPlayer extends Player {

	public VisitPlayer(String visitPlayer) {
		super(visitPlayer);
	}

	public VisitPlayer(String messageContext, int index) {
		super(messageContext, index);
	}

	String getVisitPlayer() {
		return getPlayer();
	}
}
