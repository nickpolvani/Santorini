package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AthenaTest {

    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    GameController gameController;
    Athena athena;

    @Before
    public void setUp() throws Exception {

        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        this.gameState = new GameState(players);
        this.gameController = new GameController(gameState, null);

        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.ATHENA, testPlayer));
        athena = (Athena) testPlayer.getGod();
        athena.resetGodState();

        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
        testPlayer.setWorkers(indexes);
        testPlayer.getGod().selectWorker(testPlayer.getWorkers().get(0));
    }

    @After
    public void tearDown() {
        this.gameState = null;
        this.testPlayer = null;
        this.indexes = null;
    }

    @Test
    public void move() throws Exception {

        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().addBlock();

        //fist move without moving up
        athena.move(new Tile.IndexTile(1, 1));
        assertTrue(athena.getCanMoveUp());

        //second move() moving up of one level
        athena.move(new Tile.IndexTile(0, 2));
        assertFalse(athena.getCanMoveUp());
    }
}