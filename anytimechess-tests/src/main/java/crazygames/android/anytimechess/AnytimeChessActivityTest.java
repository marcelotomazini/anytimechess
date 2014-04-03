package crazygames.android.anytimechess;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class AnytimeChessActivityTest extends ActivityInstrumentationTestCase2<AnytimeChessActivity> {

	private Solo solo;

	public AnytimeChessActivityTest() {
		super(AnytimeChessActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	protected void restart() {
		getActivity().finish();
		setActivity(null);
		getActivity();
	}
	
}