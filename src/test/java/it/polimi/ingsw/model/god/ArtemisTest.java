package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ArtemisTest {

    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    GameController gameController;
    Artemis artemis;

    @Before
    public void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");

        this.gameState = new GameState(players);
        this.gameController = new GameController(gameState, null);

        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.ARTEMIS, testPlayer));
        artemis = (Artemis) testPlayer.getGod();
        artemis.resetGodState();

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
    public void tileToMove() throws AlreadyOccupiedException, DomeAlreadyPresentException {

        Tile.IndexTile tile1 = new Tile.IndexTile(0, 0);
        Tile.IndexTile tile2 = new Tile.IndexTile(1, 0);
        gameState.getIslandBoard().getTile(tile2).getBuilding().addBlock(); //test if it can move up on level 1
        Tile.IndexTile tile3 = new Tile.IndexTile(1, 1);
        Tile.IndexTile tile4 = new Tile.IndexTile(0, 2);
        Tile.IndexTile tile5 = new Tile.IndexTile(1, 2);//tile where there is the other worker, so i can't move there
        Collection<Tile.IndexTile> foundTiles = artemis.tileToMove(artemis.currentWorker.getIndexTile());

        assertTrue(foundTiles.contains(tile1) && foundTiles.contains(tile2) && foundTiles.contains(tile3)
                && foundTiles.contains(tile4) && foundTiles.size() == 4 && !foundTiles.contains(tile5)
                && !foundTiles.contains(artemis.currentWorker.getIndexTile()));

        Tile.IndexTile tileFromTest = artemis.currentWorker.getIndexTile();
        artemis.move(tile3);
        assertEquals(artemis.currentWorker.getIndexTile(), tile3);
        assertEquals(artemis.getTileFrom(), tileFromTest);
        artemis.applyChoice(true);

        gameState.getIslandBoard().getTile(tile2).getBuilding().addBlock();//now block level is 2
        foundTiles = artemis.tileToMove(artemis.currentWorker.getIndexTile());

        Tile.IndexTile tile6 = new Tile.IndexTile(2, 1);
        Tile.IndexTile tile7 = new Tile.IndexTile(2, 2);
        Tile.IndexTile tile8 = new Tile.IndexTile(2, 0);


        assertTrue(foundTiles.contains(tile1) && foundTiles.contains(tile8)
                && foundTiles.contains(tile6) && foundTiles.contains(tile7)
                && foundTiles.contains(tile4) && !foundTiles.contains(tile5) && !foundTiles.contains(tile2)
                && !foundTiles.contains(tileFromTest) && !foundTiles.contains(tile3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void move() throws Exception {
        Tile.IndexTile positionBeforeMove = artemis.currentWorker.getIndexTile();

        artemis.move(new Tile.IndexTile(1, 1));

        assertEquals(artemis.getTileFrom(), positionBeforeMove);

        artemis.move(new Tile.IndexTile(1, 2));
        //because this is the last instruction i could write @Test(expected = IllegalArgumentException.class)

    }

    @Test
    public void isChooseAvailableTest() throws Exception {

        assertTrue(artemis.isChooseAvailable());

        Tile.IndexTile tile1 = new Tile.IndexTile(0, 0);
        gameState.getIslandBoard().getTile(tile1).getBuilding().buildDome();
        Tile.IndexTile tile2 = new Tile.IndexTile(1, 0);
        gameState.getIslandBoard().getTile(tile2).getBuilding().buildDome();
        Tile.IndexTile tile3 = new Tile.IndexTile(1, 1);
        gameState.getIslandBoard().getTile(tile3).getBuilding().buildDome();
        Tile.IndexTile tile4 = new Tile.IndexTile(0, 2);
        gameState.getIslandBoard().getTile(tile4).getBuilding().buildDome();

        assertFalse(artemis.isChooseAvailable());
    }

}