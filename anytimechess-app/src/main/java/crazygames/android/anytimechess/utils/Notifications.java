package crazygames.android.anytimechess.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.message.AcceptChallenge;
import crazygames.android.anytimechess.message.DenyChallenge;

public class Notifications {
	
	private Context context;

	public Notifications(Context context) {
		this.context = context;
	}
	
	public void notifyNewMove() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		
		int identifierIcon = context.getResources().getIdentifier("chess", "drawable", context.getPackageName());
		mBuilder.setSmallIcon(identifierIcon);
		mBuilder.setContentTitle("Nova jogada!");
		mBuilder.setContentText("Nova jogada brow!");
		mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), 0));
		mBuilder.setAutoCancel(true);
		
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			    
		int identifierAppName = context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
		mNotificationManager.notify(identifierAppName, mBuilder.build());
	}
	
	public void notifyChallenge() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
		
		int identifierIcon = context.getResources().getIdentifier("chess", "drawable", context.getPackageName());
		mBuilder.setSmallIcon(identifierIcon);
		mBuilder.setContentTitle("Novo desafio!");
		mBuilder.setContentText("Você tem um novo desafio! Aceitar?");
		mBuilder.setAutoCancel(true);
		
		mBuilder.addAction(0, "Aceito", PendingIntent.getActivity(context, 0, new Intent(context, AcceptChallenge.class), 0));
		mBuilder.addAction(0, "Não, sou cagão", PendingIntent.getActivity(context, 0, new Intent(context, DenyChallenge.class), 0));
		
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			    
		int identifierAppName = context.getResources().getIdentifier("app_name", "strings", context.getPackageName());
		mNotificationManager.notify(identifierAppName, mBuilder.build());
	}
}