package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadySetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * @author Francesco Puoti
 * We have to test player class togheter with Worker class beacuse they are too linked: player's methods call all of worker's method
 */
public class PlayerWorkerTest {
    Player player;
    GameState gameState;
    GameController gameController;

    /*
     * @Before and @After together as annotation of the same method mean that resources instanced in it, before every test,
     *      will be dumped after their uses in a test.
     */
    @Before
    @After
    public void setPlayerInstance() {
        this.player = new Player("Francesco");

    }

    @Test
    public void checkWorkersBound() {
        assertNull("After constructor, worker is not null", player.getWorker());
        Tile.IndexTile[] indexes = {new Tile.IndexTile(0, 0), new Tile.IndexTile(3, 4)};
        try {
            this.player.setWorker(Color.RED, indexes);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < player.getWorker().length; i++) {
            assertEquals("Worker's color is different from the one set whit method", player.getWorker()[i].getColor(), Color.RED);

        }

        Set<String> players = new HashSet<>();
        players.add("francesco");
        players.add("juri");
        //Testing setter and getter of positionTile.
        GameState testGame = new GameState(players);
        Tile.IndexTile testTile = testGame.getIslandBoard().getBoard()[0][1].getIndex();

        for (int i = 0; i < this.player.getWorker().length; i++) {
            player.getWorker()[i].setIndexTile(testTile);
        }

        assertEquals(player.getWorker()[0].getIndexTile(), testTile);
        assertEquals(player.getWorker()[1].getIndexTile(), testTile);
    }

    /*
    @Test
    public void checkGodChoice() throws IllegalAccessException {
        assertNull("God should be null after constructor", this.player.getGod());
        GodsFactory gf = new GodsFactory();
        God god = gf.getGod(GodNameAndDescription.HEPHAESTUS);
        this.player.setGod(god);

        assertTrue("Player.god should be equals to Hephaestus", this.player.getGod().equals(gf.getGod(GodNameAndDescription.HEPHAESTUS)));

    }
    */

    @Test
    public void checkLost() {
        assertFalse("Boolean hasLost should be false after constructor", this.player.getHasLost());

        this.player.setHasLost(Boolean.TRUE);

        assertTrue(this.player.getHasLost());
    }

}
