package it.polimi.ingsw.Model;

import it.polimi.ingsw.Exception.AlreadySetException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * @author Francesco Puoti
 * We have to test player class togheter with Worker class beacuse they are too linked: player's methods call all of worker's method
 */
public class PlayerWorkerTest {
    Player player;

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

        try {
            this.player.setWorker(Color.RED);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < player.getWorker().length; i++) {
            assertEquals("Worker's color is different from the one set whit method", player.getWorker()[i].getColor(), Color.RED);
            assertEquals("Worker's player is different from the one set whit method", player.getWorker()[i].getPlayer(), this.player);
        }


        //Testing setter and getter of positionTile.
        GameState testGame = new GameState();
        Tile testTile = testGame.getBoard().getBoard()[0][1];

        for (int i = 0; i < this.player.getWorker().length; i++) {
            player.getWorker()[i].setTile(testGame.getBoard().getBoard()[0][1]);
        }

        assertEquals(player.getWorker()[0].getTile(), testTile);
        assertEquals(player.getWorker()[1].getTile(), testTile);
    }

    @Test
    public void checkGodChoice() {
        assertNull("God should be null after constructor", this.player.getNameGod());

        this.player.setNameGod(NameGod.HEPHAESTUS);

        assertTrue("Player.god should be equals to Hephaestus", this.player.getNameGod().equals(NameGod.HEPHAESTUS));

    }

    @Test
    public void checkLost() {
        assertFalse("Boolean hasLost should be false after constructor", this.player.getHasLost());

        this.player.setHasLost(Boolean.TRUE);

        assertTrue(this.player.getHasLost());
    }

}
