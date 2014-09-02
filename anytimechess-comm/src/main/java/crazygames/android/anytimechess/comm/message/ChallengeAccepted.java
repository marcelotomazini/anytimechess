package crazygames.android.anytimechess.comm.message;


public class ChallengeAccepted extends HandShake implements Message {
	
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

	private static final String CHALLENGE_ACCEPTED = "Challenge Accepted";

	public ChallengeAccepted(String destination) {
		super(destination);
	}

	@Override
	protected String buildMessage() {
		return CHALLENGE_ACCEPTED;
	}
}
