package com.marcelotomazini.android.antitheftadvanced;

import junit.framework.Assert;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import com.jayway.android.robotium.solo.Solo;

public class AntiTheftAdvancedActivityTest extends ActivityInstrumentationTestCase2<AntiTheftAdvancedActivity> {

	private Solo solo;

	public AntiTheftAdvancedActivityTest() {
		super(AntiTheftAdvancedActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		clearConfiguration();
		restart();
	}

	@Override
	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	@LargeTest
	public void testInstrumentation() {
		Assert.assertFalse(solo.isCheckBoxChecked("Activate WIFI automatically"));
		Assert.assertFalse(solo.isCheckBoxChecked("Activate Mobile Data automatically"));

		solo.clickOnText("Activate WIFI automatically");
		solo.clickOnText("Activate Mobile Data automatically");

		Assert.assertTrue(solo.isCheckBoxChecked("Activate WIFI automatically"));
		Assert.assertTrue(solo.isCheckBoxChecked("Activate Mobile Data automatically"));

		// try {
		// Thread.sleep(1000*30);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
	}

	@LargeTest
	public void testAppConfiguration() {
		createConfiguration();
		restart();

		Assert.assertTrue(solo.isCheckBoxChecked("Activate WIFI automatically"));
		Assert.assertTrue(solo.isCheckBoxChecked("Activate Mobile Data automatically"));
	}

	private void restart() {
		getActivity().finish();
		setActivity(null);
		getActivity();
	}
	
	private void clearConfiguration() {
		SharedPreferences prefs = solo.getCurrentActivity().getSharedPreferences(AntiTheftAdvanced.APP_PACKAGE, Context.MODE_PRIVATE);
		prefs.edit().putBoolean(AntiTheftAdvancedActivity.ACTIVATE_WIFI, false).commit();
		prefs.edit().putBoolean(AntiTheftAdvancedActivity.ACTIVATE_MOBILE_DATA, false).commit();
	}

	private void createConfiguration() {
		SharedPreferences prefs = solo.getCurrentActivity().getSharedPreferences(AntiTheftAdvanced.APP_PACKAGE, Context.MODE_PRIVATE);
		prefs.edit().putBoolean(AntiTheftAdvancedActivity.ACTIVATE_WIFI, true).commit();
		prefs.edit().putBoolean(AntiTheftAdvancedActivity.ACTIVATE_MOBILE_DATA, true).commit();
	}
}