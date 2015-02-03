package crazygames.android.anytimechess.layouts;

import java.util.Arrays;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import crazygames.android.anytimechess.SimpleListAdapter;
import crazygames.android.anytimechess.layouts.menu.MenuItem;
import crazygames.android.anytimechess.utils.Resources;

public class WelcomeView extends ListView {

	public WelcomeView(Context context) {
		super(context);
		
		TextView title = new MenuItem(getContext(), "Anytime Chess");

		ImageView imageView = new ImageView(getContext());
		imageView.setImageResource(new Resources(getContext()).getMainIcon());
		
		TextView description = new MenuItem(getContext(), "BETA FOREVER!!!");
		
		setAdapter(new SimpleListAdapter(Arrays.asList(title, imageView, description)));
	}
}
