package crazygames.android.anytimechess;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class BoardAdapter implements ListAdapter {

	private final List<Square> itemList = new ArrayList<Square>();

	public BoardAdapter(final Context context) {
		boolean white = false;
		for (int row = 1; row <= 8; row++)
			for (char col = 'a'; col <= 'h'; col++) {
				final Square square = new Square(context, white ? Color.GRAY : Color.LTGRAY, row, col);
				if(col != 'h')
					white = !white;

				itemList.add(square);
			}
	}

	@Override
	public int getCount() {
		return itemList.size();
	}

	@Override
	public Object getItem(final int index) {
		return itemList.get(index);
	}

	@Override
	public long getItemId(final int index) {
		return index;
	}

	@Override
	public int getItemViewType(final int arg0) {
		return 0;
	}

	@Override
	public View getView(final int index, final View arg1, final ViewGroup arg2) {
		return itemList.get(index);
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	@Override
	public void registerDataSetObserver(final DataSetObserver arg0) {
	}

	@Override
	public void unregisterDataSetObserver(final DataSetObserver arg0) {
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(final int arg0) {
		return true;
	}

	public List<Square> getAllItems() {
		return itemList;
	}

}
