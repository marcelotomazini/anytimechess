package crazygames.android.anytimechess.layouts;

import crazygames.android.anytimechess.state.StateManager;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonsLayout extends LinearLayout {

	private Button btnRefresh;
	private String player;

	public ButtonsLayout(Context context) {
		super(context);
	}

	private void createButtons() {
		removeAllViews();
		btnRefresh = new Button(getContext());
		btnRefresh.setText("Reenviar Jogada");
		addView(btnRefresh);
	}

	public void load(String player) {
		createButtons();
		this.player = player;
		refreshListeners();
	}

	private void refreshListeners() {
		btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new StateManager(getContext()).refresh(player);
			}
		});
	}
}
