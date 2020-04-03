package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

/**
 * @author Francesco Puoti
 */

public class GameStateTest {
    GameState testGame;

    @Before
    @After
    public void prepareTest() {
        Set<String> players = new TreeSet<>();
        players.add("juri");
        players.add("francesco");
        players.add("Nick");
        this.testGame = new GameState(players);
    }

    @Test
    public void GameState() {
        assertNotNull("players null after constructor!", testGame.getPlayers());
        assertFalse("players not empty after constructor", testGame.getPlayers().isEmpty());
    }

    @Test
    public void checkSetter() {

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
