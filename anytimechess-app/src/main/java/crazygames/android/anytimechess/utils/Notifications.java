package crazygames.android.anytimechess.utils;

import static android.content.Context.NOTIFICATION_SERVICE;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Notifications {
	
	private Context context;

	public Notifications(Context context) {
		this.context = context;
	}
	
	public void notifyNewMove() {
		Notification notificacao = new Notification(R.drawable.chess, "Nova jogada!", System.currentTimeMillis());
		
		notificacao.setLatestEventInfo(context, "Nova jogada!", "Nova jogada brow!", PendingIntent.getActivity(context, 0, new Intent(context, AnytimeChessActivity.class), 0));
		
		notificacao.flags |= Notification.FLAG_AUTO_CANCEL;
		notificacao.defaults |= Notification.DEFAULT_ALL;
		
		((NotificationManager)context.getSystemService(NOTIFICATION_SERVICE)).notify(R.string.app_name, notificacao);
	}

}
