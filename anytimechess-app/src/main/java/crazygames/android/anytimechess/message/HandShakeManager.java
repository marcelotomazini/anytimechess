package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.message.Challenge.CHALLENGE_TYPE;
import static crazygames.android.anytimechess.comm.message.ChallengeAccepted.CHALLENGE_ACCEPTED;
import static crazygames.android.anytimechess.comm.message.ChallengeDenied.CHALLENGE_DENIED;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Notifications;

public class HandShakeManager {
	
	private Context context;
	private Notifications notifications;

	public HandShakeManager(Context context) {
		this.context = context;
		notifications = new Notifications(context);
	}
	
	public void resolve(String player, String message) {
		if (message.contains(CHALLENGE_TYPE))
			notifyChallengeReceived(player);
		if (message.contains(CHALLENGE_ACCEPTED))
			prepareNewGame(player);
		if (message.contains(CHALLENGE_DENIED))
			notifyChallengeDenied(player);
	}

	public void newChallenge(String player) {
		Challenge challenge = new Challenge(null, player);
		new SMSSender().send(challenge);
	}

	private void prepareNewGame(String player) {
		new StateManager(context).create(player);
		notifications.notifyNewMove(player);
	}

	private void notifyChallengeReceived(final String player) {
		notifications.notifyChallenge(player);
	}

	private void notifyChallengeDenied(String player) {
		notifications.notifyChallengeDenied(player);
	}
}
