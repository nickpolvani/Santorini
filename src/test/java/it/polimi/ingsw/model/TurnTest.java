package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void checkConstructor() {

        this.testGame.setTurn();
        this.testTurn = testGame.getTurn();

        assertEquals("testTurn.myGame should be equals to testGame", testTurn.getMyGame(), testGame);
        assertEquals("CurrentPlayer should be equals to 'Francesco' ", testTurn.getCurrentPlayer().getNickname(), "Francesco");

        assertTrue("CanMoveUp should be true after setter", testTurn.getCanMoveUp());
        assertTrue("HasToMove should be true after setter", testTurn.getHasToMove());
        assertTrue("HasToBuild should be true after setter", testTurn.getHasToBuild());


    }

}
