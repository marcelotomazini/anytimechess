package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.LinearLayout;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Alerts;
import crazygames.android.anytimechess.utils.Alerts.SureListener;

public class ButtonsLayout extends LinearLayout {

	private String player;
	private Alerts alerts;

	public ButtonsLayout(Context context) {
		super(context);
		alerts = new Alerts(getContext());
	}

	public void load(String player) {
		this.player = player;
		createButtons();
	}

	private void createButtons() {
		removeAllViews();
		createButton("Reenviar Jogada", new RefreshListener());
		createButton("Desistir do Jogo", new GiveUpListener());
	}

	private void createButton(String text, SureListener listener) {
		Button button = new Button(getContext());
		button.setText(text);
		button.setOnClickListener(alerts.createConfirmListener(listener));
		addView(button);
	}

	private class RefreshListener implements SureListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			StateManager stateManager = new StateManager(getContext());
			if (stateManager.isMyTurn(player))
				return;
			
			stateManager.refresh(player);
			new Alerts(getContext()).displayBundleMessage("move.sent");
		}
	}

	private class GiveUpListener implements SureListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
		}
	}
}
