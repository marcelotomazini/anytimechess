package crazygames.android.anytimechess.message;

import android.content.Context;
import android.telephony.SmsMessage;
import crazygames.android.anytimechess.comm.message.HandShake;
import crazygames.android.anytimechess.state.StateManager;

public class MessageManager {
	
	private Context context;

	public MessageManager(Context context) {
		this.context = context;
	}

	public void routeMessage(SmsMessage sms) {
		String player = sms.getOriginatingAddress();
		String message = sms.getMessageBody();
		
		if (message.contains(HandShake.HEADER))
			new HandShakeManager(context).resolve(player, message);
		else
			new StateManager(context).update(message);
	}
}
