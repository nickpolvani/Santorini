package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.GodDescription;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Francesco Puoti
 */

public class PlayerTest {

    static GameState gameState;
    static GameController gameController;
    static Tile.IndexTile[] indexes0;

    @BeforeClass
    public static void setUp() {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");

        gameState = new GameState(players);
        gameController = new GameController(gameState);

        indexes0 = new Tile.IndexTile[2];
        indexes0[0] = new Tile.IndexTile(0, 1);
        indexes0[1] = new Tile.IndexTile(1, 2);
    }

    @AfterClass
    public static void tearDown() {
        gameState = null;
        gameController = null;
        indexes0 = null;
    }

    @Test
    public void constructorTest() {
        assertEquals(gameState.getPlayers().get(0).getNickname(), "Francesco");
        assertEquals(gameState.getPlayers().get(1).getNickname(), "Nick");
        assertEquals(gameState.getPlayers().get(2).getNickname(), "Juri");
    }

    @Test
    public void setWorkerTest() {
        Player testPlayer = gameState.getPlayers().get(0);
        assertNull("Workers'array should be null after constructor without any call to the setter method", testPlayer.getWorkers());

        try {
            testPlayer.setWorkers(indexes0);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        try {
            testPlayer.setWorkers(indexes0);
        } catch (AlreadySetException e) {
            System.out.println("Tried how the method trows exception when someone tries to set workers another time. Correct behaviour checked");
        }
    }

    @Test
    public void setGodTest() {
        Player testPlayer = gameState.getPlayers().get(0);
        assertNull("God not already set: property should be null", testPlayer.getGod());
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.PAN, testPlayer));
        assertEquals(testPlayer.getGod(), gameState.getGodsFactory().getGod(GodDescription.PAN, testPlayer));
    }
}
