package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

}