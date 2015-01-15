package crazygames.android.anytimechess.utils;


public class TelephonyUtils {

	public static String filterNumber(String playerNumber) {
		if (playerNumber == null)
			return null;

		playerNumber = playerNumber.replace("(", "");
		playerNumber = playerNumber.replace(")", "");
		playerNumber = playerNumber.replace("-", "");
		playerNumber = playerNumber.trim();

		int length = playerNumber.length();
		return playerNumber.substring(length < 8 ? 0 : length - 8, length);
	}
}
