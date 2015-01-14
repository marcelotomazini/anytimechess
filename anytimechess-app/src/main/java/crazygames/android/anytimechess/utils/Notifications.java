package crazygames.android.anytimechess.utils;

import static android.app.Notification.DEFAULT_ALL;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static crazygames.android.anytimechess.message.ChallengeService.PLAYER_KEY;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.message.ChallengeService.AcceptChallenge;
import crazygames.android.anytimechess.message.ChallengeService.DenyChallenge;

public class Notifications {
	
	private Context context;

	public Notifications(Context context) {
		this.context = context;
	}
	
	public void notifyNewMove() {
		NotificationCompat.Builder mBuilder = createDefaultBuilder();
		
		mBuilder.setContentTitle("Nova jogada!");
		mBuilder.setContentText("Nova jogada brow!");
		mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), 0));
		
		notify(mBuilder);
	}

	public void notifyChallenge(String player) {
		NotificationCompat.Builder mBuilder = createDefaultBuilder();
		
		mBuilder.setContentTitle("Novo desafio!");
		mBuilder.setContentText("Você tem um novo desafio! Aceitar?");
		
		Bundle extras = new Bundle();
		extras.putString(PLAYER_KEY, player);
		
		Intent intent = new Intent(context, AcceptChallenge.class);
		intent.putExtras(extras);
		mBuilder.addAction(0, "Yep", PendingIntent.getService(context, 0, intent, FLAG_UPDATE_CURRENT));
				
		intent = new Intent(context, DenyChallenge.class);
		intent.putExtras(extras);
		mBuilder.addAction(0, "Nope", PendingIntent.getService(context, 0, intent, FLAG_UPDATE_CURRENT));
		
		notify(mBuilder);
	}

	private NotificationCompat.Builder createDefaultBuilder() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setDefaults(DEFAULT_ALL);
		mBuilder.setSmallIcon(context.getResources().getIdentifier("chess", "drawable", context.getPackageName()));
		mBuilder.setAutoCancel(true);
		return mBuilder;
	}

	private void notify(NotificationCompat.Builder mBuilder) {
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			    
		int identifierAppName = context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
		mNotificationManager.notify(identifierAppName, mBuilder.build());
	}
}