package crazygames.android.anytimechess.message;

import static crazygames.android.anytimechess.comm.state.Header.HEADER;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import br.com.pilovieira.ermacs.Ermacs;

public class SMSReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		if (extras == null)
			return;

		SmsMessage sms = SmsMessage.createFromPdu((byte[]) ((Object[]) extras.get("pdus"))[0]);
		if (!isAnytimeChessMessage(sms.getMessageBody()))
			return;
		
		try {
			new MessageManager(context).routeMessage(sms);			
		} catch (Exception e) {
			Ermacs.addErmac(context, e);
		}
		
		abortBroadcast();
	}

	private boolean isAnytimeChessMessage(String message) {
		return message.contains(HEADER);
	}
}
