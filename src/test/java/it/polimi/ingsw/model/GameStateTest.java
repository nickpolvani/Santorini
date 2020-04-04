package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GameStateTest {

    GameState gameState;
    List<Player> players;

    @Before
    public void setUp() throws Exception {
        Set<String> users = new LinkedHashSet<>();
        users.add("Nick");
        users.add("Fra");
        users.add("Juri");
        gameState = new GameState(users);
        players = gameState.getPlayers();
    }

    @After
    public void tearDown() throws Exception {
        gameState = null;
        players = null;
    }


    @Test
    public void getNextPlayerTest() {
        // test with 3 players
        assertEquals(gameState.getNextPlayer(players.get(0)), players.get(1));
        assertEquals(gameState.getNextPlayer(players.get(1)), players.get(2));
        assertEquals(gameState.getNextPlayer(players.get(2)), players.get(0));

        // test with 2 players
        Set<String> users = new LinkedHashSet<>();
        users.add("Nick");
        users.add("Fra");
        gameState = new GameState(users);
        players = gameState.getPlayers();
        assertEquals(gameState.getNextPlayer(players.get(0)), players.get(1));
        assertEquals(gameState.getNextPlayer(players.get(1)), players.get(0));

    }


}