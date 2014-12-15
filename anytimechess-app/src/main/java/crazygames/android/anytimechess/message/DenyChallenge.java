package crazygames.android.anytimechess.message;

import android.app.Activity;

public class DenyChallenge extends Activity {
	
	@Override
	protected void onResume() {
		new HandShakeManager(this).sendChallengeDenied();
		super.onResume();
	}
}
