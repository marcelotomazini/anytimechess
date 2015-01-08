package crazygames.android.anytimechess.state;

import static crazygames.android.anytimechess.comm.item.Header.HEADER;
import static crazygames.android.anytimechess.utils.TelephonyUtils.filterNumber;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.message.SMSSender;

@RunWith(MockitoJUnitRunner.class)
public class StateManagerTest {

	private static final String HOME = "4498476508";
	private static final String VISIT = "4491257086";
	private static final String STATE_SEQ_1 = "atchess00001004498476508004491257086WHITERNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";
	private static final String STATE_SEQ_2 = "atchess00002004498476508004491257086BLACKRNBQK1eBNRPPPPPPPP--------------------------------pppppppprnbqk8ebnr";

	@Mock private MyNumberResolver myNumber;
	@Mock private StateStamp stateStamp;
	@Mock private Game game;
	@Mock private SMSSender sender;
	
	@Rule public ExpectedException thrown = ExpectedException.none();
	
	private StateManager subject;
	
	@Before
	public void setUp() {
		when(stateStamp.getStateMessage(HOME)).thenReturn(STATE_SEQ_1);
		
		subject = new StateManager(myNumber, stateStamp, sender);
	}
	
	@Test
	public void createNewState() {
		when(myNumber.getMyNumber()).thenReturn(HOME);
		
		State newState = subject.create(VISIT);
		
		assertNotNull("State should not be null", newState);
		assertEquals("Header", HEADER, newState.getHeader());
		assertEquals("Home Player", HOME, newState.getHome());
		assertEquals("Visit Player", filterNumber(VISIT), newState.getVisit());
		assertEquals("Turn Sequence", 1, newState.getTurnSequence());
		assertNotNull("game should not be null", newState.getGame());
		
		InOrder inOrder = Mockito.inOrder(myNumber, stateStamp);
		inOrder.verify(myNumber).getMyNumber();
		inOrder.verify(stateStamp).setStateMessage(any(State.class));
	}
	
	@Test
	public void dontCreateStateWithoutPlayer() {
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Invalid player identifier");
		
		subject.create(null);
	}
	

	@Test
	public void getPersistedState() {
		State state = subject.get(HOME);
		
		assertNotNull("State should be loaded", state);
		
		verify(stateStamp).getStateMessage(HOME);
	}

	@Test
	public void getInexistentState() {
		when(stateStamp.getStateMessage(HOME)).thenReturn(null);
		
		State state = subject.get(HOME);
		
		assertNull("State should not be loaded", state);
		
		verify(stateStamp).getStateMessage(HOME);
	}

	@Test
	public void updateNewState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1);
		
		subject.update(STATE_SEQ_2);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp).setStateMessage(any(State.class));
	}

	@Test
	public void updateCreatingNewState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		subject.update(STATE_SEQ_2);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp).setStateMessage(any(State.class));
	}

	@Test
	public void doNothingWhenReceiveSameState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1);
		
		subject.update(STATE_SEQ_1);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(stateStamp, never()).setStateMessage(any(State.class));
	}

	@Test
	public void dontUpdateOutOfOrderStates() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_2);
		
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("State received is old! (Out of order)");
		
		subject.update(STATE_SEQ_1);
	}
	
	@Test
	public void sendNewState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1);
		
		State state = subject.send(VISIT, new Game());
		
		assertNotNull("State should not be null", state);
		assertEquals("",  state.build(), STATE_SEQ_2);
		
		InOrder inOrder = Mockito.inOrder(stateStamp, sender);
		
		inOrder.verify(stateStamp).getStateMessage(VISIT);
		inOrder.verify(stateStamp).setStateMessage(any(State.class));
		inOrder.verify(sender).send(any(State.class));
	}
	
	@Test
	public void refreshState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(STATE_SEQ_1);
		
		subject.refresh(VISIT);
		
		InOrder inOrder = Mockito.inOrder(stateStamp, sender);
		
		inOrder.verify(stateStamp).getStateMessage(VISIT);
		inOrder.verify(sender).send(any(State.class));
	}

	@Test
	public void doNothingWhenRefreshInexistentState() {
		when(stateStamp.getStateMessage(VISIT)).thenReturn(null);
		
		subject.refresh(VISIT);
		
		verify(stateStamp).getStateMessage(VISIT);
		verify(sender, never()).send(any(State.class));
	}
}
