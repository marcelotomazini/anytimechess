package crazygames.android.anytimechess.message;

import android.app.Activity;

public class AcceptChallenge extends Activity {
	
	@Override
	protected void onResume() {
		new HandShakeManager(this).sendChallengeAccepted();
		super.onResume();
	}
}