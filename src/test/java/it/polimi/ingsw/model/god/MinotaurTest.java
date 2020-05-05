package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MinotaurTest {

    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    GameController gameController;
    Minotaur minotaur;


    @Before
    public void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        this.gameState = new GameState(players);
        this.gameController = new GameController(gameState, null);

        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.MINOTAUR, testPlayer));
        minotaur = (Minotaur) testPlayer.getGod();
        minotaur.resetGodState();
        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
        testPlayer.setWorkers(indexes);
        testPlayer.getGod().selectWorker(testPlayer.getWorkers()[0]);

        indexes[0] = new Tile.IndexTile(1, 1);
        indexes[1] = new Tile.IndexTile(3, 2);
        gameState.getPlayers().get(1).setWorkers(indexes);

        indexes[0] = new Tile.IndexTile(4, 1);
        indexes[1] = new Tile.IndexTile(4, 2);
        gameState.getPlayers().get(2).setWorkers(indexes);

        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().buildDome();
    }

    @After
    public void tearDown() {
        this.gameState = null;
        this.testPlayer = null;
        this.indexes = null;
    }

    @Test
    public void getTurnOperations() {
    }

    @Test
    public void tileToMove() throws DomeAlreadyPresentException {

        Tile.IndexTile tile1 = new Tile.IndexTile(0, 0);
        Tile.IndexTile tile2 = new Tile.IndexTile(1, 0);
        gameState.getIslandBoard().getTile(tile2).getBuilding().addBlock(); //test if it can move up on level 1
        Tile.IndexTile tile3 = new Tile.IndexTile(1, 1);//tile with opponent worker
        Tile.IndexTile tile4 = new Tile.IndexTile(0, 2);//tile with dome
        Tile.IndexTile tile5 = new Tile.IndexTile(1, 2);//tile where there is the other worker of the same team
        Collection<Tile.IndexTile> foundTiles = minotaur.tileToMove(minotaur.worker.getIndexTile());

        assertTrue(foundTiles.contains(tile1) && foundTiles.contains(tile2) && foundTiles.contains(tile3)
                && !foundTiles.contains(tile4) && !foundTiles.contains(tile5)
                && !foundTiles.contains(minotaur.worker.getIndexTile()));


        gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 1)).getBuilding().buildDome();
        foundTiles = minotaur.tileToMove(minotaur.worker.getIndexTile());

        assertTrue(foundTiles.contains(tile1) && foundTiles.contains(tile2) && !foundTiles.contains(tile3)
                && !foundTiles.contains(tile4) && !foundTiles.contains(tile5)
                && !foundTiles.contains(minotaur.worker.getIndexTile()));

    }

    @Test
    public void move() throws AlreadyOccupiedException {

        try {
            minotaur.move(new Tile.IndexTile(1, 2));
        } catch (Exception e) {
            System.out.println(e.getMessage() + ": Exception correctly thrown");
        }

        Tile.IndexTile whereMove = new Tile.IndexTile(1, 1);
        Worker opponentWorker = gameState.getIslandBoard().getTile(whereMove).getCurrentWorker();
        minotaur.move(whereMove);
        assertEquals(gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 1)).getCurrentWorker(), opponentWorker);
        assertEquals(gameState.getIslandBoard().getTile(whereMove).getCurrentWorker(), minotaur.worker);
    }

    @Test
    public void checkBackwards() throws AlreadyOccupiedException {

        assertTrue(minotaur.checkBackwardsTile(new Tile.IndexTile(0, 1), new Tile.IndexTile(1, 1)));

        minotaur.selectWorker(testPlayer.getWorkers()[1]);
        minotaur.move(new Tile.IndexTile(1, 3));
        assertEquals(minotaur.worker.getIndexTile(), new Tile.IndexTile(1, 3));

        assertFalse(minotaur.checkBackwardsTile(new Tile.IndexTile(1, 3), new Tile.IndexTile(1, 4)));

    }
}