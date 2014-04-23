package crazygames.android.anytimechess;

import android.content.Context;
import android.widget.ImageView;

public class PieceView extends ImageView {

	public PieceView(final Context context, final String imageName) {
		super(context);
		final int identifier = getResources().getIdentifier(imageName, "drawable", context.getPackageName());
		setImageResource(identifier);
	}

}
