package crazygames.android.anytimechess.utils;

import static android.app.Notification.DEFAULT_ALL;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.NOTIFICATION_SERVICE;
import static crazygames.android.anytimechess.utils.Preferences.PLAYER;
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
	private Resources resources;

	public Notifications(Context context) {
		this.context = context;
		this.resources = new Resources(context);
	}
	
	public void notifyChallengeDenied(String player) {
		NotificationCompat.Builder mBuilder = createDefaultBuilder();
		
		String playerName = TelephonyUtils.resolvePlayerName(context, player);
		mBuilder.setContentTitle(Messages.getString("challenge.denied.title", playerName));
		mBuilder.setContentText(Messages.getString("challenge.denied.message", playerName));
		
		mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), FLAG_UPDATE_CURRENT));

		notify(mBuilder);
	}

	public void notifyNewMove(String player) {
		NotificationCompat.Builder mBuilder = createDefaultBuilder();
		
		mBuilder.setContentTitle(Messages.getString("new.move"));
		mBuilder.setContentText(Messages.getString("new.move.detail", getPlayerName(player)));
		
		Bundle extras = new Bundle();
		extras.putString(PLAYER, player);
		
		Intent intent = new Intent(context, AnytimeChessActivity.class);
		intent.putExtras(extras);
		
		mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, intent, FLAG_UPDATE_CURRENT));
		
		notify(mBuilder);
	}

	public void notifyChallenge(String player) {
		NotificationCompat.Builder mBuilder = createDefaultBuilder();
		
		mBuilder.setContentTitle(Messages.getString("new.challenge"));
		mBuilder.setContentText(Messages.getString("new.challenge.detail", getPlayerName(player)));
		
		Bundle extras = new Bundle();
		extras.putString(PLAYER, player);
		
		Intent intent = new Intent(context, AcceptChallenge.class);
		intent.putExtras(extras);
		mBuilder.addAction(0, "Accept", PendingIntent.getService(context, 0, intent, FLAG_UPDATE_CURRENT));
				
		intent = new Intent(context, DenyChallenge.class);
		intent.putExtras(extras);
		mBuilder.addAction(0, "Deny", PendingIntent.getService(context, 0, intent, FLAG_UPDATE_CURRENT));
		
		notify(mBuilder);
	}

	private String getPlayerName(String player) {
		return TelephonyUtils.resolvePlayerName(context, player);
	}

	public void cancel() {
		NotificationManager manager = getManager();
		manager.cancel(resources.getIdentifierAppName());
	}

	private NotificationCompat.Builder createDefaultBuilder() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setDefaults(DEFAULT_ALL);
		mBuilder.setSmallIcon(resources.getMainIcon());
		mBuilder.setAutoCancel(true);
		return mBuilder;
	}

	private NotificationManager getManager() {
		return (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
	}

	private void notify(NotificationCompat.Builder mBuilder) {
		NotificationManager manager = getManager();
		manager.notify(resources.getIdentifierAppName(), mBuilder.build());
	}
}