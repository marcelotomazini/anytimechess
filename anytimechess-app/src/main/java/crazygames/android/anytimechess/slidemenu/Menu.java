package crazygames.android.anytimechess.slidemenu;

import java.util.Arrays;

import android.content.Context;
import android.widget.ListView;
import android.widget.TextView;
import crazygames.android.anytimechess.SimpleListAdapter;

public class Menu extends ListView {

	public Menu(Context context) {
		super(context);
		
		TextView tv1 = new TextView(context);
        tv1.setText("item1");
        TextView tv2 = new TextView(context);
        tv2.setText("item2");
        
		setAdapter(new SimpleListAdapter(Arrays.asList(tv1, tv2)));
	}

}
