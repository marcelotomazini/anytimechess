package crazygames.android.anytimechess;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import crazygames.android.anytimechess.slidemenu.Menu;
import crazygames.android.anytimechess.slidemenu.SlideMenu;
import crazygames.android.anytimechess.utils.Messages;


public class AnytimeChessActivity extends Activity {

	public static final int PICK_CONTACT_REQUEST = 1;
	private SlideMenu slideMenu;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		slideMenu = new SlideMenu(this);
        setContentView(slideMenu);
        
        View contentView = new Board(this);
        slideMenu.addView(contentView, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));

        Menu primaryMenu = new Menu(this);
        slideMenu.addView(primaryMenu, new SlideMenu.LayoutParams(300,
        		SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Check which request we're responding to
	    if (requestCode == PICK_CONTACT_REQUEST) {
	        if (resultCode == RESULT_OK) {
	        	Uri contactUri = data.getData();
	            String[] projection = {Phone.NUMBER, Phone.DISPLAY_NAME};

	            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
	            cursor.moveToFirst();

	            int column = cursor.getColumnIndex(Phone.NUMBER);
	            // number to challenge
	            String number = cursor.getString(column);
	            System.out.println(number);
	            
	            Builder ok = new AlertDialog.Builder(this);
	            ok.setMessage(Messages.getString("challenge.sent", cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))));
	            ok.setPositiveButton("OK", null);
	            ok.show();
	        }
	    }
	    slideMenu.close(true);
	}
}
