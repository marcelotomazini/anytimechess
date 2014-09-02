package crazygames.android.anytimechess.sms;

import android.telephony.SmsManager;
import crazygames.android.anytimechess.comm.message.Message;

public class SMSSender {
	
	public void send(Message message) {
		send(message.getDestination(), message.build());
	}
	
	private void send(String numero, String comando) {
		SmsManager.getDefault().sendTextMessage(numero, null, comando, null, null);			
	}
}
