package crazygames.android.anytimechess;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import crazygames.android.anytimechess.slidemenu.Menu;
import crazygames.android.anytimechess.slidemenu.SlideMenu;


public class AnytimeChessActivity extends Activity {

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SlideMenu slideMenu = new SlideMenu(this);
        setContentView(slideMenu);
        
        View contentView = new Board(this);
        slideMenu.addView(contentView, new SlideMenu.LayoutParams(
                SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.MATCH_PARENT,
                SlideMenu.LayoutParams.ROLE_CONTENT));

        Menu primaryMenu = new Menu(this);
        slideMenu.addView(primaryMenu, new SlideMenu.LayoutParams(300,
        		SlideMenu.LayoutParams.MATCH_PARENT, SlideMenu.LayoutParams.ROLE_PRIMARY_MENU));
	}

}
