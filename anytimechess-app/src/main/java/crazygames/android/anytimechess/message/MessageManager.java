package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import android.content.Context;
import android.telephony.SmsMessage;
import crazygames.android.anytimechess.comm.message.HandShake;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Notifications;

public class MessageManager {
	
	private Context context;

	public MessageManager(Context context) {
		this.context = context;
	}

	public void routeMessage(SmsMessage sms) {
		String player = filterNumber(sms.getOriginatingAddress());
		String message = sms.getMessageBody();
		
		if (message.contains(HandShake.HEADER))
			new HandShakeManager(context).resolve(player, message);
		else
			update(player, message);
	}

	private void update(String player, String message) {
		new StateManager(context).update(player, message);
		new Notifications(context).notifyNewMove(player);
	}
}
