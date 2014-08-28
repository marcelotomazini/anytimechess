package crazygames.android.anytimechess.slidemenu;

import android.view.View;

public interface ScrollDetector {
	public boolean canScrollHorizontal(View v, int direction);

	public boolean canScrollVertical(View v, int direction);
}
