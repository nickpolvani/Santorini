package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Francesco Puoti
 */

public class GameStateTest {
    GameState testGame;

    @Before
    @After
    public void prepareTest() {
        this.testGame = new GameState();
    }

    @Test
    public void checkConstuctor() {
        assertNotNull("players null after constructor!", testGame.getPlayers());
        assertTrue("players not empty after constructor", testGame.getPlayers().isEmpty());
    }

    @Test
    public void checkSetter() {
        this.testGame.addPlayer(new Player("Francesco"));
        this.testGame.addPlayer(new Player("Nick"));
        this.testGame.addPlayer(new Player("Juri"));

        /*
        checking if method correctly controls number of players
         */
        try {
            this.testGame.addPlayer(new Player("ExcessPlayer"));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        assertNull(this.testGame.getTurn());
        try {
            this.testGame.setTurn(new Turn(this.testGame));
        } catch (AlreadySetException e) {
            System.out.println("Turn already set");
        }

        assertEquals(this.testGame, this.testGame.getTurn().getGameState());


        Player currentTestPlayer = this.testGame.getPlayers().get(0);
        Player nextTestPlayer = this.testGame.getNextPlayer(currentTestPlayer);
        assertEquals(nextTestPlayer, this.testGame.getPlayers().get(1));

        currentTestPlayer = nextTestPlayer;
        nextTestPlayer = this.testGame.getNextPlayer(currentTestPlayer);
        assertEquals(nextTestPlayer, this.testGame.getPlayers().get(2));

        currentTestPlayer = nextTestPlayer;
        nextTestPlayer = this.testGame.getNextPlayer(currentTestPlayer);
        assertEquals(nextTestPlayer, this.testGame.getPlayers().get(0));

    }
}
