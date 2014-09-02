package crazygames.android.anytimechess.comm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import crazygames.android.anytimechess.comm.item.GameStateTest;
import crazygames.android.anytimechess.comm.item.HomePlayerTest;
import crazygames.android.anytimechess.comm.item.TurnSequenceTest;
import crazygames.android.anytimechess.comm.item.TurnTest;
import crazygames.android.anytimechess.comm.item.VisitPlayerTest;
import crazygames.android.anytimechess.comm.message.ChallengeAcceptedTest;
import crazygames.android.anytimechess.comm.message.ChallengeTest;
import crazygames.android.anytimechess.comm.message.StateTest;

@RunWith(Suite.class)
@SuiteClasses({
	GameStateTest.class,
	HomePlayerTest.class,
	TurnSequenceTest.class,
	TurnTest.class,
	VisitPlayerTest.class,
	StateTest.class,
	ChallengeTest.class,
	ChallengeAcceptedTest.class})
public class AllTests {

}
