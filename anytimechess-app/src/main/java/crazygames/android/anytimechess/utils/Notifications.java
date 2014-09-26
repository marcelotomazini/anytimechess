package crazygames.android.anytimechess.utils;

import static android.content.Context.NOTIFICATION_SERVICE;
import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
}