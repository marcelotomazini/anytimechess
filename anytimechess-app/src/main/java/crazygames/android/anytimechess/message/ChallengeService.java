package crazygames.android.anytimechess.message;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import crazygames.android.anytimechess.comm.message.ChallengeAccepted;
import crazygames.android.anytimechess.comm.message.ChallengeDenied;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Notifications;

public abstract class ChallengeService extends Service {

	public static final String PLAYER_KEY = "player";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String player = getPlayer(intent);
		processChallenge(player);
		
		cancelNotification();
		stopSelf();
		return START_STICKY;
	}
	
	private String getPlayer(Intent intent) {
		Bundle extras = intent.getExtras();
		
		if (extras == null || extras.getString(PLAYER_KEY) == null)
			throw new RuntimeException("Problema ao obter extras. Chame suporte!");
		
		return extras.getString(PLAYER_KEY);
	}

	private void cancelNotification() {
		new Notifications(this).cancel();
	}

	protected abstract void processChallenge(String player);

	public static class AcceptChallenge extends ChallengeService {
		
		@Override
		protected void processChallenge(String player) {
			new StateManager(this).clear(player);
			
			ChallengeAccepted ca = new ChallengeAccepted(player);
			new SMSSender().send(ca);
		}
	}

	public static class DenyChallenge extends ChallengeService {
		
		@Override
		protected void processChallenge(String player) {
			ChallengeDenied cd = new ChallengeDenied(player);
			new SMSSender().send(cd);
		}
	}
}
