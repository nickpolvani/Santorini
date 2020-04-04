package it.polimi.ingsw.model.god;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Puoti
 */
public class PanTest {
    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    Tile.IndexTile newIndex;


    @Before
    public void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        this.gameState = new GameState(players);
        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodNameAndDescription.PAN));

        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
        testPlayer.setWorker(Color.RED, indexes);

        testPlayer.getGod().selectWorker(testPlayer.getWorker()[0]);

    }

    @After
    public void tearDown() throws Exception {
        this.gameState = null;
        this.testPlayer = null;
        this.indexes = null;
    }

    @Test
    public void move() {
        assertEquals(testPlayer.getWorker()[0].getIndexTile(), indexes[0]);
        newIndex = gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getIndex();
        try {
            testPlayer.getGod().move(newIndex);
        } catch (AlreadyOccupiedException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(testPlayer.getWorker()[0].getIndexTile(), newIndex);

        newIndex = gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getIndex();

        try {
            testPlayer.getGod().move(newIndex);
        } catch (AlreadyOccupiedException e) {
            System.out.println("tile is already occupied");
        }


    }

    @Test
    public void getTurnOperations() {

    }
}