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
    public static void setUp() throws Exception {
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
    public void tearDown() throws Exception {
        gameState = null;
    }

    @Test
    public void getGod() {

        player1.setGod(gameState.getGodsFactory().getGod(GodNameAndDescription.PAN, player1));
        gameState.getGodsFactory().getGod(GodNameAndDescription.APOLLO, player2);
        gameState.getGodsFactory().getGod(GodNameAndDescription.MINOTAUR, player3);


        try {
            gameState.getGodsFactory().getGod(GodNameAndDescription.DEMETER, player1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        God pan = gameState.getGodsFactory().getGod(GodNameAndDescription.PAN, player1);
        assertEquals(pan, player1.getGod());


    }
}