package crazygames.android.anytimechess.comm.message;


public class ChallengeDenied extends HandShake {
	
	/*
	 * ░░░░░░▄▄█▀▀░░░░░░░░░▀▀██▄░░░░░░░
	 * ░░░░▄█▀░▄██▀▀▀▀█▄▄░░░▄▄▄██▄░░░░░
	 * ░░▄█▀░░█▀░░░▄█▄░░██░██░▄▄░▀█▄░░░
	 * ░▄█░░░░██▄░░▀█▀▄▄█▀░▀█░▀█▀░░██░░
	 * ▄█░░░░░░░▀▀▀▀▀▀▀▀░░░░░▀▄▄▄▄█▀█▄░
	 * ██░░░░░░░░░░░░░░▄▄▄▄▄░░░░░░░░▀█░
	 * █░░░░░░░░░░░▄▄█▀▀▀▀▀▀▀█▄▄░░░░░█░
	 * █▄░░░░░░░░░░█▀░░░░░░░░░▀█░░░░░█░
	 * ██░░░░░░░░░░██▄░░░░░░▄▄█▀░░░░██░
	 * ░█▄░░▀█▄▄░░░░░▀▀█████▀▀░▄█▀▀░█░░
	 * ░░█▄░░░░▀▀▀▄▄░░░░░░░░▄▄▀▀░░░▄▀░░
	 * ░░░▀█▄░░░░░░▀▀██▄▄▄▄█▀░░░░░█░░░░
	 * ░░░░░▀█▄░░░░░░░▄▀░░░▀▄▄▄▄▄▀░░░░░
	 * ░░░░░░░▀▀█▄▄▄▄█▀░░░░░░░██▄▄▄░░░░
	 * ░░░░░░░░░░░▄█▀▀▄▄▄▄▄▄▄█░░░░░▀▄▄░
	 * ░░░░░░░░░▄▀░░▄█▀░▀█▄░░░▀▀▀▄▄▄▄█░
	 */

	public static final String CHALLENGE_DENIED = "CD";

	public ChallengeDenied(String destination) {
		super(destination);
	}

	@Override
	protected String buildMessage() {
		return CHALLENGE_DENIED;
	}

	@Override
	protected String handShakeType() {
		return CHALLENGE_DENIED;
	}
}
