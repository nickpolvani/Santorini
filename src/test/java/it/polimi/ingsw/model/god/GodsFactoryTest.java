package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.GameState;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class GodsFactoryTest {

    static GameState gameState;

    @BeforeClass
    public static void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        gameState = new GameState(players);
    }

    @After
    public void tearDown() throws Exception {
        gameState = null;
    }

    @Test
    public void getGod() {

        try {
            gameState.getGodsFactory().getGod(GodNameAndDescription.PAN);
            gameState.getGodsFactory().getGod(GodNameAndDescription.APOLLO);
            gameState.getGodsFactory().getGod(GodNameAndDescription.MINOTAUR);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }

        try {
            gameState.getGodsFactory().getGod(GodNameAndDescription.DEMETER);
        } catch (IllegalAccessException e) {
            System.out.println("Impossible to create a new god's instance");
        }


        try {
            gameState.getGodsFactory().getGod(GodNameAndDescription.PAN);
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }


    }
}