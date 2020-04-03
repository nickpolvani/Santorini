package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Francesco Puoti
 */

public class TurnTest {

    Turn testTurn;
    GameState testGame;


    @Before
    @After
    public void prepareTest() {
        this.testGame = new GameState();
        this.testGame.addPlayer(new Player("Francesco"));
        this.testGame.addPlayer(new Player("Niccolo"));
        this.testGame.addPlayer(new Player("Juri"));
    }

    /**
     * This test allows to check both Constructor and switchTurn method. Therefore also setter and getter methods are tested.
     * Moreover, trying only the initialization of the turn is enough, because the "getNextPlayer" method of class
     * GameState is tested in GameStateTest.java.
     */
    @Test
    public void checkConstructor() throws AlreadySetException {

        this.testGame.setTurn(new Turn(this.testGame));
        this.testTurn = testGame.getTurn();
     //TODO


    }

}
