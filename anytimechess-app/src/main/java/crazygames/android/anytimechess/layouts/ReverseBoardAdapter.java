package crazygames.android.anytimechess.layouts;

import static java.util.Collections.reverse;
import android.content.Context;

public class ReverseBoardAdapter extends BoardAdapter {

	public ReverseBoardAdapter(Context context) {
		super(context);
		reverse(itemList);
	}
}
