package crazygames.android.anytimechess;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class SimpleListAdapter extends BaseAdapter {

	private final List<? extends View> items;
	
	public SimpleListAdapter(List<? extends View> items) {
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return items.get(position);
	}

}
