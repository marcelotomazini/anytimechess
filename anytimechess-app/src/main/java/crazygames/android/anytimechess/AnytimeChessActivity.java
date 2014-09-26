package crazygames.android.anytimechess;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import crazygames.android.anytimechess.layouts.MainLayout;
import crazygames.android.anytimechess.message.HandShakeManager;
import crazygames.android.anytimechess.slidemenu.Menu;
import crazygames.android.anytimechess.slidemenu.SlideMenu;
import crazygames.android.anytimechess.utils.Messages;
import crazygames.android.anytimechess.utils.Notifications;


public class AnytimeChessActivity extends Activity {

	public static final int PICK_CONTACT_REQUEST = 1;
	private SlideMenu slideMenu;
	private MainLayout mainLayout;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		slideMenu = new SlideMenu(this);
        setContentView(slideMenu);
        
        mainLayout = new MainLayout(this);
        slideMenu.addView(mainLayout, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));

        Menu primaryMenu = new Menu(this);
        slideMenu.addView(primaryMenu, new SlideMenu.LayoutParams(300,
        		SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));
        
        Notifications.init(this);
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
	            String number = cursor.getString(column);
	            // TODO pilo -> number to challenge
	            System.out.println(number);
	            
	            //much need so pls refactor doge
	            new HandShakeManager(this).challenge(number);
	            
	            mainLayout.newGame();
	            
	            Notifications.displayMessage(Messages.getString("challenge.sent", cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))));
	        }
	    }
	    slideMenu.close(true);
	}

}