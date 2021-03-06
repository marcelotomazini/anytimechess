package crazygames.android.anytimechess;

import static crazygames.android.anytimechess.utils.Preferences.PLAYER;
import me.tangke.slidemenu.SlideMenu;
import me.tangke.slidemenu.SlideMenu.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.view.Window;
import crazygames.android.anytimechess.layouts.MainLayout;
import crazygames.android.anytimechess.message.HandShakeManager;
import crazygames.android.anytimechess.utils.Alerts;
import crazygames.android.anytimechess.utils.TelephonyUtils;


public class AnytimeChessActivity extends Activity {

	public static final int PICK_CONTACT_REQUEST = 1;
	private SlideMenu slideMenu;
	private GameRoomMenu gameRoomMenu;
	private MainLayout mainLayout;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		slideMenu = new SlideMenu(this);
        setContentView(slideMenu);

        mainLayout = new MainLayout(this);
        slideMenu.addView(mainLayout, getMainLayoutParameters());

        View optionsMenu = new OptionsMenu(this);
        slideMenu.addView(optionsMenu, getOptionsMenuParameters());

        gameRoomMenu = new GameRoomMenu(this);
        slideMenu.addView(gameRoomMenu, getGameRoomMenuParameters());
	}

	private LayoutParams getMainLayoutParameters() {
		return new SlideMenu.LayoutParams(
				SlideMenu.LayoutParams.MATCH_PARENT,
				SlideMenu.LayoutParams.MATCH_PARENT,
				SlideMenu.LayoutParams.ROLE_CONTENT);
	}

	private LayoutParams getOptionsMenuParameters() {
		return new SlideMenu.LayoutParams(300, SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU);
	}

	private LayoutParams getGameRoomMenuParameters() {
		return new SlideMenu.LayoutParams(300, SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_SECONDARY_MENU);
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameRoomMenu.refresh();
		loadGame();
		slideMenu.close(true);
	}

	private void loadGame() {
		Bundle extras = getIntent().getExtras();
		if (extras == null || extras.getString(PLAYER) == null)
			return;
		
		mainLayout.load(extras.getString(PLAYER));
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
	            
	            String player = TelephonyUtils.filterNumber(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
				new HandShakeManager(this).newChallenge(player);
	            
	            String playerName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
				new Alerts(this).displayBundleMessage("challenge.sent", playerName);
	        }
	    }
	    slideMenu.close(true);
	}
	
	public SlideMenu getSlideMenu() {
		return slideMenu;
	}
	
	MainLayout getMainLayout() {
		return mainLayout;
	}
}