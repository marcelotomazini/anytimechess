package crazygames.android.anytimechess;

import android.app.Activity;
import android.os.Bundle;

public class AnytimeChessActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new Board(this));
	}
}
