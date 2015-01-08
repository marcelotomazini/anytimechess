package crazygames.android.anytimechess.state;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import crazygames.android.anytimechess.comm.message.Message;
import crazygames.android.anytimechess.utils.Preferences;

@RunWith(MockitoJUnitRunner.class)
public class StateStampTest {

	private static final String PLAYER = "99998888";
	private static final String MESSAGE_BUILDED = "messageBuilded";
	
	@Mock private Preferences preferences;
	@Mock private Context context;
	@Mock private SharedPreferences sharedPreferences;
	@Mock private Editor editor;
	@Mock private Message message;

	private StateStamp subject;

	@Before
	public void setUp() {
		when(message.getDestination()).thenReturn(PLAYER);
		when(message.build()).thenReturn(MESSAGE_BUILDED);
		when(preferences.getSharedPreferences()).thenReturn(sharedPreferences);
		when(sharedPreferences.edit()).thenReturn(editor);
		
		subject = new StateStamp(preferences);
	}

	@Test
	public void setStateMessage() {
		subject.setStateMessage(message);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).putString(PLAYER, MESSAGE_BUILDED);
		inOrder.verify(editor).commit();
	}
	
	@Test
	public void getStateMessageStamped() {
		when(sharedPreferences.getString(PLAYER, null)).thenReturn(MESSAGE_BUILDED);
		
		String stateMessage = subject.getStateMessage(PLAYER);
		
		assertEquals("Message", MESSAGE_BUILDED, stateMessage);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getString(PLAYER, null);
	}

	@Test
	public void getStateMessageNull() {
		String stateMessage = subject.getStateMessage(PLAYER);
		
		assertNull("Message should be null", stateMessage);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getString(PLAYER, null);
	}

}
