package crazygames.android.anytimechess.sms;

import static crazygames.android.anytimechess.comm.item.Header.HEADER;
import crazygames.android.anytimechess.state.StateStamp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle extras = intent.getExtras();

		if (extras == null)
			return;

		SmsMessage sms = SmsMessage.createFromPdu((byte[]) ((Object[]) extras.get("pdus"))[0]);
		if (!isAnytimeChessMessage(sms.getMessageBody()))
			return;
		
		manageMessage(context, sms);
		abortBroadcast();
	}

	private boolean isAnytimeChessMessage(String message) {
		return message.contains(HEADER);
	}

	private void manageMessage(Context context, SmsMessage sms) {
		//TODO Pilo create message identifier
		new StateStamp(context).setStateMessage(sms);
	}
}