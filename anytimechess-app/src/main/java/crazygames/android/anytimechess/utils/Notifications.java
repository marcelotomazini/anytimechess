package crazygames.android.anytimechess.utils;

import static android.content.Context.NOTIFICATION_SERVICE;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import crazygames.android.anytimechess.AnytimeChessActivity;

public class Notifications {
	
	private Context context;

	public Notifications(Context context) {
		this.context = context;
	}
	
	public void notifyNewMove() {
		final int identifierIcon = context.getResources().getIdentifier("chess", "drawable", context.getPackageName());
		Notification notificacao = new Notification(identifierIcon, "Nova jogada!", System.currentTimeMillis());
		
		notificacao.setLatestEventInfo(context, "Nova jogada!", "Nova jogada brow!", PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), 0));
		
		notificacao.flags |= Notification.FLAG_AUTO_CANCEL;
		notificacao.defaults |= Notification.DEFAULT_ALL;
		
		final int identifierAppName = context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
		((NotificationManager)context.getSystemService(NOTIFICATION_SERVICE)).notify(identifierAppName, notificacao);
	}
	
	public void notifyChallenge(String player) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		
		int identifierIcon = context.getResources().getIdentifier("chess", "drawable", context.getPackageName());
		mBuilder.setSmallIcon(identifierIcon);
		mBuilder.setContentTitle("Notification Alert, Click Me!");
		mBuilder.setContentText("Hi, This is Android Notification Detail!");
		
		Bundle bundle = new Bundle();
		bundle.putString("player", player);
		
		
//		Intent resultIntent = new Intent(context, AcceptChallenge.class);
//		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//		stackBuilder.addParentStack(AnytimeChessActivity.class);
//		
//		// Adds the Intent that starts the Activity to the top of the stack
//		stackBuilder.addNextIntent(resultIntent);
//		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT, bundle);
//		
		
		Intent intent = new Intent(context, AnytimeChessActivity.class);
		intent.getExtras().putString("player", player);
		mBuilder.addAction(0, "Aceito", PendingIntent.getActivity(context, 0, intent, 0));
		mBuilder.addAction(0, "Não, sou cagão", PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), 0, bundle));
		
//		mBuilder.setContentIntent(resultPendingIntent);
		

		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			    
		// notificationID allows you to update the notification later on.
		int identifierAppName = context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
		mNotificationManager.notify(identifierAppName, mBuilder.build());
	}
}