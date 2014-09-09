package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.message.Challenge.CHALLENGE_TYPE;
import static crazygames.android.anytimechess.comm.message.ChallengeAccepted.CHALLENGE_ACCEPTED;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import static crazygames.android.anytimechess.utils.TelephonyUtils.getTelephonyNumber;
import android.content.Context;
import crazygames.android.anytimechess.comm.message.Challenge;
import crazygames.android.anytimechess.comm.message.ChallengeAccepted;
import crazygames.android.anytimechess.state.StateManager;

public class HandShakeManager {
	
	private Context context;

	public HandShakeManager(Context context) {
		this.context = context;
	}
	
	public void resolve(String player, String message) {
		if (message.contains(CHALLENGE_TYPE))
			acceptChallenge(player);
		if (message.contains(CHALLENGE_ACCEPTED))
			challengeAccepted(player);
	}

	public void challenge(String player) {
		Challenge challenge = new Challenge(getTelephonyNumber(context), filterNumber(player));
		new SMSSender().send(challenge);
	}

	private void acceptChallenge(String player) {
		ChallengeAccepted challengeAccepted = new ChallengeAccepted(filterNumber(player));
		new SMSSender().send(challengeAccepted);
	}

	private void challengeAccepted(String player) {
		new StateManager(context).create(player);
	}
}
