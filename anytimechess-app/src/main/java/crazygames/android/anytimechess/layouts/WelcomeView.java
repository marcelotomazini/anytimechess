package crazygames.android.anytimechess.layouts;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import crazygames.android.anytimechess.utils.Resources;

public class WelcomeView extends LinearLayout {

	public WelcomeView(Context context) {
		super(context);
		
		ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(new Resources(getContext()).getMainIcon());
		addView(imageView);
		setGravity(Gravity.CENTER);
		setBackgroundColor(android.graphics.Color.DKGRAY);
	}
}
