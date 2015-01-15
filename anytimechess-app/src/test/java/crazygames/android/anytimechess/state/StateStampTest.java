package crazygames.android.anytimechess.state;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.HashSet;

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
	public void setNewStateMessage() {
		subject.setNewStateMessage(message);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).putStringSet(PLAYER, setSigned());
		inOrder.verify(editor).commit();
	}

	private HashSet<String> setSigned() {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add(message.build());
		hashSet.add("HOME");
		
		return hashSet;
	}

	private HashSet<String> setUnsigned() {
		HashSet<String> hashSet = new HashSet<String>();
		hashSet.add(message.build());
		
		return hashSet;
	}

	@Test
	public void setStateMessageWhenIHome() {
		doReturn(setSigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		subject.setStateMessage(message);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).putStringSet(PLAYER, setSigned());
		inOrder.verify(editor).commit();
	}

	@Test
	public void setStateMessageWhenIVisit() {
		doReturn(setUnsigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		subject.setStateMessage(message);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).putStringSet(PLAYER, setUnsigned());
		inOrder.verify(editor).commit();
	}

	@Test
	public void setStateMessageWithoutOldStateMessage() {
		doReturn(new HashSet<String>()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		subject.setStateMessage(message);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).putStringSet(PLAYER, setUnsigned());
		inOrder.verify(editor).commit();
	}

	@Test
	public void clearStateMessage() {
		subject.clearStateMessage(PLAYER);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).edit();
		inOrder.verify(editor).remove(PLAYER);
		inOrder.verify(editor).commit();
	}
	
	@Test
	public void getStateMessageWhenIHome() {
		doReturn(setSigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		String stateMessage = subject.getStateMessage(PLAYER);
		
		assertEquals("Message", MESSAGE_BUILDED, stateMessage);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
	}

	@Test
	public void getStateMessageWhenIVisit() {
		doReturn(setUnsigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		String stateMessage = subject.getStateMessage(PLAYER);
		
		assertEquals("Message", MESSAGE_BUILDED, stateMessage);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
	}

	@Test
	public void getStateMessageNull() {
		String stateMessage = subject.getStateMessage(PLAYER);
		
		assertNull("Message should be null", stateMessage);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
	}

	@Test
	public void stateSigned() {
		doReturn(setSigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		boolean isSigned = subject.isSignedState(PLAYER);
		
		assertTrue("State should be signed", isSigned);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
	}

	@Test
	public void stateNotSigned() {
		doReturn(setUnsigned()).when(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
		
		boolean isSigned = subject.isSignedState(PLAYER);
		
		assertFalse("State should not be signed", isSigned);
		
		InOrder inOrder = Mockito.inOrder(preferences, sharedPreferences, editor);
		
		inOrder.verify(preferences).getSharedPreferences();
		inOrder.verify(sharedPreferences).getStringSet(PLAYER, new HashSet<String>());
	}

}
