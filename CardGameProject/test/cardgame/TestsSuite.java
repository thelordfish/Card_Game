package cardgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CardTest.class,
    CardDeckTest.class,
    PlayerTest.class,
    TurnConcurrencyTest.class,
    CardGameIntegrationTest.class
})


public class TestsSuite {

}
