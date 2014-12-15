package crazygames.android.anytimechess;


import me.tangke.slidemenu.SlideMenu;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import crazygames.android.anytimechess.message.HandShakeManager;
import crazygames.android.anytimechess.state.MyNumber;
import crazygames.android.anytimechess.utils.Messages;
import crazygames.android.anytimechess.utils.NotificationUtils;


public class AnytimeChessActivity extends Activity {

	public static final int PICK_CONTACT_REQUEST = 1;
	private SlideMenu slideMenu;
	private GameRoomMenu gameRoomMenu;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		new MyNumber(this).resolveMyNumber();
		
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

		slideMenu = new SlideMenu(this);
        setContentView(slideMenu);

        // Setup the content
        View contentView = new View(this);
        slideMenu.addView(contentView,
        		new SlideMenu.LayoutParams(
        				SlideMenu.LayoutParams.MATCH_PARENT,
        				SlideMenu.LayoutParams.MATCH_PARENT,
        				SlideMenu.LayoutParams.ROLE_CONTENT));

        // Setup the primary menu
        View primaryMenu = new OptionsMenu(this);
        slideMenu.addView(primaryMenu,
        		new SlideMenu.LayoutParams(300, SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));

        gameRoomMenu = new GameRoomMenu(this);
        slideMenu.addView(gameRoomMenu,
        		new SlideMenu.LayoutParams(300, SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_SECONDARY_MENU));

        NotificationUtils.init(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		gameRoomMenu.refresh();
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
	            
	            new HandShakeManager(this).challenge(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
	            
	            NotificationUtils.displayMessage(Messages.getString("challenge.sent", cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME))));
	        }
	    }
	    slideMenu.close(true);
	}
}