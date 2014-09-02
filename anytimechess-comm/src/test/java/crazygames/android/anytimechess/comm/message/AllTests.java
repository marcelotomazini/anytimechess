package crazygames.android.anytimechess.comm.message;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

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
