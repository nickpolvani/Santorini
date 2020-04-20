package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GodsFactoryTest {

    static GameState gameState;
    static Player player1;
    static Player player2;
    static Player player3;

    @BeforeClass
    public static void setUp() {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        gameState = new GameState(players);
        player1 = gameState.getPlayers().get(0);
        player2 = gameState.getPlayers().get(1);
        player3 = gameState.getPlayers().get(2);
    }

    @After
    public void tearDown() {
        gameState = null;
    }

    @Test
    public void getGod() {

        player1.setGod(gameState.getGodsFactory().getGod(GodDescription.PAN, player1));
        gameState.getGodsFactory().getGod(GodDescription.APOLLO, player2);
        gameState.getGodsFactory().getGod(GodDescription.MINOTAUR, player3);


        try {
            gameState.getGodsFactory().getGod(GodDescription.DEMETER, player1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        God pan = gameState.getGodsFactory().getGod(GodDescription.PAN, player1);
        assertEquals(pan, player1.getGod());


    }
}