package crazygames.android.anytimechess;

import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;


public class AnytimeChessApplicationTests extends ApplicationTestCase<AnytimeChess> {

	public AnytimeChessApplicationTests() {
		super(AnytimeChess.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}


	@SmallTest
	public void testPreconditions() {
	}


	@MediumTest
	public void testSimpleCreate() {
		createApplication();
	}

}