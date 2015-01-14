package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.comm.item.Header.HEADER;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.verification.api.VerificationData;
import org.mockito.invocation.Invocation;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.verification.VerificationMode;

import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.message.SMSSender;

@RunWith(MockitoJUnitRunner.class)
public class StateManagerTest {

	private static final String HOME = "98476508";
	private static final String VISIT = "91257086";
	private static final String STATE_SEQ_1_WHITE = "atchess00001004498476508004491257086WHITERNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	private static final String STATE_SEQ_2_BLACK = "atchess00002004498476508004491257086BLACKRNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";

	@Mock private MyNumberResolver myNumber;
	@Mock private StateStamp stateStamp;
	@Mock private Game game;
	@Mock private SMSSender sender;
	
	@Rule public ExpectedException thrown = ExpectedException.none();
	
	private StateManager subject;
	
	@Before
	public void setUp() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1_WHITE);
		when(myNumber.getMyNumber()).thenReturn(HOME);
		
		subject = new StateManager(myNumber, stateStamp, sender);
	}
	
	@Test
	public void createNewState() {
		
		State newState = subject.create(VISIT);
		
		assertNotNull("State should not be null", newState);
		assertEquals("Header", HEADER, newState.getHeader());
		assertEquals("Home Player", HOME, newState.getHome());
		assertEquals("Visit Player", VISIT, newState.getVisit());
		assertEquals("Turn Sequence", 1, newState.getTurnSequence());
		assertNotNull("game should not be null", newState.getGame());
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(VISIT, null));
	}

	@Test
	public void clearState() {
		subject.clear(VISIT);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp).clearStateMessage(VISIT);
	}

	@Test
	public void doNothingWhenClearStateWithoutPlayer() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		subject.clear(VISIT);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp, never()).clearStateMessage(anyString());
	}
	
	@Test
	public void dontCreateStateWithoutPlayer() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Invalid player identifier");
		
		subject.create(null);
	}
	
	@Test
	public void getPersistedState() {
		State state = subject.get(VISIT);
		
		assertNotNull("State should be loaded", state);
		
		verify(stateStamp).getStateMessage(VISIT);
	}

	@Test
	public void getInexistentState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		State state = subject.get(VISIT);
		
		assertNull("State should not be loaded", state);
		
		verify(stateStamp).getStateMessage(VISIT);
	}

	@Test
	public void updateNewState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1_WHITE);
		
		subject.update(STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(VISIT, null));
	}

	@Test
	public void updateNewStateYouAreVisit() {
		when(myNumber.getMyNumber()).thenReturn(VISIT);
		when(stateStamp.getStateMessage(HOME)).thenReturn(STATE_SEQ_1_WHITE);
		
		subject.update(STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp).getStateMessage(HOME);
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(HOME, null));
	}

	@Test
	public void updateCreatingNewState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		subject.update(STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(VISIT, null));
	}

	@Test
	public void updateCreatingNewStateYouAreVisit() {
		when(myNumber.getMyNumber()).thenReturn(VISIT);
		when(stateStamp.getStateMessage(HOME)).thenReturn(null);
		
		subject.update(STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp).getStateMessage(HOME);
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(HOME, null));
	}

	@Test
	public void doNothingWhenReceiveSameState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1_WHITE);
		
		subject.update(STATE_SEQ_1_WHITE);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp, never()).setStateMessage(any(StateMessage.class));
	}

	@Test
	public void dontUpdateOutOfOrderStates() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_2_BLACK);
		
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("State received is old! (Out of order)");
		
		subject.update(STATE_SEQ_1_WHITE);
	}
	
	@Test
	public void sendNewState() {
		State oldState = new State(STATE_SEQ_1_WHITE);
		
		State state = subject.send(oldState, new Game());
		
		assertNotNull("State should not be null", state);
		assertEquals("",  state.build(), STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(VISIT, null));
		verify(sender, new StateMessageVerification()).send(new StateMessage(VISIT, null));
	}

	@Test
	public void sendNewStateYouAreVisit() {
		when(myNumber.getMyNumber()).thenReturn(VISIT);
		State oldState = new State(STATE_SEQ_1_WHITE);
		
		State state = subject.send(oldState, new Game());
		
		assertNotNull("State should not be null", state);
		assertEquals("",  state.build(), STATE_SEQ_2_BLACK);
		
		verify(myNumber, times(2)).getMyNumber();
		verify(stateStamp, new StateMessageVerification()).setStateMessage(new StateMessage(HOME, null));
		verify(sender, new StateMessageVerification()).send(new StateMessage(HOME, null));
	}
	
	@Test
	public void refreshState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1_WHITE);
		
		subject.refresh(VISIT);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(sender, new StateMessageVerification()).send(new StateMessage(VISIT, null));
	}

	@Test
	public void isHomeTurn() {		
		boolean myTurn = subject.isMyTurn(new State(STATE_SEQ_1_WHITE));
		
		assertTrue("Should be home turn", myTurn);
		
		verify(myNumber).getMyNumber();
	}

	@Test
	public void isVisitTurn() {		
		boolean myTurn = subject.isMyTurn(new State(STATE_SEQ_2_BLACK));
		
		assertFalse("Should be visit turn", myTurn);
		
		verify(myNumber).getMyNumber();
	}

	@Test
	public void iAmVistAndIsMyTurn() {
		when(myNumber.getMyNumber()).thenReturn(VISIT);
		
		boolean myTurn = subject.isMyTurn(new State(STATE_SEQ_2_BLACK));
		
		assertTrue("Should be my turn", myTurn);
		
		verify(myNumber).getMyNumber();
	}

	@Test
	public void doNothingWhenRefreshInexistentState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		subject.refresh(VISIT);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(sender, never()).send(any(StateMessage.class));
	}
	
	private class StateMessageVerification implements VerificationMode {
		
		@Override
		public void verify(VerificationData data) {
			for (Invocation invocation : data.getAllInvocations())
				if (invocation.getArguments()[0] instanceof StateMessage) {
					Object object = data.getWanted().getInvocation().getArguments()[0];
					assertNotNull("Parameter should be StateMessage", object);
					assertEquals("Parameter should be StateMessage type", StateMessage.class, object.getClass());
					
					assertEquals("Destination Message", ((StateMessage)invocation.getArguments()[0]).getDestination(), ((StateMessage)object).getDestination());
					return;
				}
			
			fail("Message not invoked correctly");
		}
	}
}
