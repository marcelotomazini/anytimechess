package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.message.Challenge.CHALLENGE_TYPE;
import static crazygames.android.anytimechess.comm.message.ChallengeAccepted.CHALLENGE_ACCEPTED;
import static crazygames.android.anytimechess.comm.message.ChallengeDenied.CHALLENGE_DENIED;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Alerts;
import crazygames.android.anytimechess.utils.Notifications;
import crazygames.android.anytimechess.utils.TelephonyUtils;

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
		Challenge challenge = new Challenge(null, player);
		new SMSSender().send(challenge);
	}

	private void prepareNewGame(String player) {
		new StateManager(context).create(player);
		new Notifications(context).notifyNewMove(player);
	}

	private void notifyChallengeReceived(final String player) {
		new Notifications(context).notifyChallenge(player);
	}

	private void challengeDenied(String player) {
		String playerName = TelephonyUtils.resolvePlayerName(context, player);
		new Alerts(context).displayMessage(playerName + " pulou fora!"); //TODO Pilo extract string
	}
}
