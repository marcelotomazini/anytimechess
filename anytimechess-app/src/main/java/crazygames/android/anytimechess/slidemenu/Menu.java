package crazygames.android.anytimechess.slidemenu;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import crazygames.android.anytimechess.AnytimeChessActivity;
import crazygames.android.anytimechess.SimpleListAdapter;
import crazygames.android.anytimechess.utils.Messages;

public class Menu extends ListView {

	public Menu(final Activity context) {
		super(context);

		TextView tv1 = new TextView(context);
		tv1.setText(Messages.getString("new.game"));
		TextView tv2 = new TextView(context);
		tv2.setText(Messages.getString("about"));

		tv1.setOnClickListener(openContacts(context));
		tv2.setOnClickListener(about(context));

		setAdapter(new SimpleListAdapter(Arrays.asList(tv1, tv2)));
	}

	private OnClickListener about(final Activity context) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Builder ok = new AlertDialog.Builder(context);
	            ok.setMessage("AnytimeChess");
	            ok.setPositiveButton("OK", null);
	            ok.show();
			}
		};
	}

	private OnClickListener openContacts(final Activity context) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				openContacts();
			}

			private void openContacts() {
				Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
				pickContactIntent.setType(Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
				context.startActivityForResult(pickContactIntent, AnytimeChessActivity.PICK_CONTACT_REQUEST);
			}
		};
	}

}
