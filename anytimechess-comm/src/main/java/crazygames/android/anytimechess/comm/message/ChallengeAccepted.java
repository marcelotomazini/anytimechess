package crazygames.android.anytimechess.comm.message;


public class ChallengeAccepted extends HandShake {
	
	/*
	 * ░░░░░▄█▀░▄▄▀▀▀▀▀█▄░░░░░▄▄██▄░░░░
	 * ░░░▄█▀░░█▀░████░░██░░█▀░▄▄▄░▀▄░░
	 * ░░██░░░░█░░▀▀▀▀░▄█▀░░█▄░▀██▀░██░
	 * ░██░░░░░░▀▀▀▀▀▀▀▀░░░░░▀▀▄▄▄▄▄▀██
	 * ░█▀░░░░░░░░░░░░░░░░░░░░░░░░░░░░█
	 * ██░░░░░░░░░░░░░░░░▄▄▄▄████░░░░░█
	 * ██░░░░░░░░░░░░░▄██▀▀▀░▄▄█▀░░░░░█
	 * ░█▄░░░░░░░░░░░▀▀▀░░░░░▀▀░░░░░░▄█
	 * ░▀█░░░░░░░░░░░░░░░░░░░░░░░░░░░█▀
	 * ░░▀█░░░░░░░░░░░░░░░░░░░░░░░░▄█▀░
	 * ░░░▀█▄░░░░░░░░░░░░░░░░░░░░░▄█▀░░
	 * ░░░░░▀██▄░░░░░░░░░░░░░░░▄▄█▀░░░░
	 * ░░░░░░░░▀██▄▄▄▄▄▄▄▄▄▄▄██▀░░░░░░░
	 * ░░░░░░░░░░░░▀▀▀▀████████▄▄░░░░░░
	 * ░░░░░░░░░░░░░▄██▀░░█▄░░░▀▀██▄░░░
	 * ░░░░░░░░░░░░▄█▀░░▄▄██▄███▀███░░░
	 */

	public static final String CHALLENGE_ACCEPTED = "CA";

	public ChallengeAccepted(String destination) {
		super(destination);
	}

	@Override
	protected String buildMessage() {
		return CHALLENGE_ACCEPTED;
	}

	@Override
	protected String handShakeType() {
		return CHALLENGE_ACCEPTED;
	}
}
