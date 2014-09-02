package crazygames.android.anytimechess.comm.message;


public class Invite implements Message {

	private Header header;
	private InviteContent inviteMessage;

	public Invite(String homePlayer) {
		header = new Header();
		inviteMessage = new InviteContent(homePlayer);
	}

	@Override
	public String build() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(header.build());
		buffer.append(inviteMessage.build());
		
		return buffer.toString();
	}
}
