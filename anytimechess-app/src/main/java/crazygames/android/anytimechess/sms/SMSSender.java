package crazygames.android.anytimechess.sms;

import android.telephony.SmsManager;
import crazygames.android.anytimechess.comm.message.MessageContext;
import crazygames.android.anytimechess.engine.game.Game;

public class SMSSender {
	
	public void send(String player, Game game) {
		//TODO Pilo complete parameters
		MessageContext messageContext = new MessageContext(0, "", "", "", game);
		send("", messageContext.buildMessage());
	}
	
	private void send(String numero, String comando) {
		SmsManager.getDefault().sendTextMessage(numero, null, comando, null, null);			
	}
}
