package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.message.Challenge.CHALLENGE_TYPE;
import static crazygames.android.anytimechess.comm.message.ChallengeAccepted.CHALLENGE_ACCEPTED;
import static crazygames.android.anytimechess.comm.message.ChallengeDenied.CHALLENGE_DENIED;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import static crazygames.android.anytimechess.utils.TelephonyUtils.getTelephonyNumber;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.NotificationUtils;
import crazygames.android.anytimechess.utils.Notifications;

public class HandShakeManager {
	
	private Context context;

	public HandShakeManager(Context context) {
		this.context = context;
	}
	
	public void resolve(String player, String message) {
		if (message.contains(CHALLENGE_TYPE))
			notifyChallengeReceived(player);
		if (message.contains(CHALLENGE_ACCEPTED))
			prepareNewGame(player);
		if (message.contains(CHALLENGE_DENIED))
			challengeDenied(player);
	}

	public void newChallenge(String player) {
		Challenge challenge = new Challenge(getTelephonyNumber(context), filterNumber(player));
		new SMSSender().send(challenge);
	}

	private void prepareNewGame(String player) {
		new StateManager(context).create(player);
		new Notifications(context).notifyNewMove();
	}

	private void notifyChallengeReceived(final String player) {
		new Notifications(context).notifyChallenge(filterNumber(player));
	}

	private void challengeDenied(String player) {
		new NotificationUtils(context).displayMessage(player + "O bixin tremeu na base!"); //TODO Pilo extract string
	}
}
