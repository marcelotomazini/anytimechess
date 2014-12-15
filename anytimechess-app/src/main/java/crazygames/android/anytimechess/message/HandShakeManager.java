package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.message.Challenge.CHALLENGE_TYPE;
import static crazygames.android.anytimechess.comm.message.ChallengeAccepted.CHALLENGE_ACCEPTED;
import static crazygames.android.anytimechess.comm.message.ChallengeDenied.CHALLENGE_DENIED;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import static crazygames.android.anytimechess.utils.TelephonyUtils.getTelephonyNumber;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.comm.message.ChallengeAccepted;
import crazygames.android.anytimechess.comm.message.ChallengeDenied;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.NotificationUtils;
import crazygames.android.anytimechess.utils.Notifications;

public class HandShakeManager {
	
	private static String player;  //TODO Pilo hole shit static (learn Bundle)!
	
	private Context context;

	public HandShakeManager(Context context) {
		this.context = context;
	}
	
	public void resolve(String player, String message) {
		if (message.contains(CHALLENGE_TYPE))
			notifyChallenge(player);
		if (message.contains(CHALLENGE_ACCEPTED))
			prepareNewGame(player);
		if (message.contains(CHALLENGE_DENIED))
			challengeDenied(player);
	}

	public void challenge(String player) {
		Challenge challenge = new Challenge(getTelephonyNumber(context), filterNumber(player));
		new SMSSender().send(challenge);
	}

	private void prepareNewGame(String player) {
		new StateManager(context).create(player);
		new Notifications(context).notifyNewMove();
	}

	private void challengeDenied(String player) {
		new NotificationUtils(context).displayMessage(player + "O bixin tremeu na base!"); //TODO Pilo extract string
	}

	private void notifyChallenge(final String player) {
		HandShakeManager.player = player;
		new Notifications(context).notifyChallenge();
	}
	
	void sendChallengeAccepted() {
		ChallengeAccepted ca = new ChallengeAccepted(filterNumber(player));
		new SMSSender().send(ca);
	}

	void sendChallengeDenied() {
		ChallengeDenied cd = new ChallengeDenied(filterNumber(player));
		new SMSSender().send(cd);
	}
}
