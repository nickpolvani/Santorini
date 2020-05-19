package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class CharonTest {

    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    GameController gameController;
    Charon charon;


    @Before
    public void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        this.gameState = new GameState(players);
        this.gameController = new GameController(gameState, null);

        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.CHARON, testPlayer));
        charon = (Charon) testPlayer.getGod();
        charon.resetGodState();
        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
        testPlayer.setWorkers(indexes);
        testPlayer.getGod().selectWorker(testPlayer.getWorkers().get(0));

        indexes[0] = new Tile.IndexTile(1, 1);
        indexes[1] = new Tile.IndexTile(3, 2);
        gameState.getPlayers().get(1).setWorkers(indexes);

        indexes[0] = new Tile.IndexTile(4, 1);
        indexes[1] = new Tile.IndexTile(4, 2);
        gameState.getPlayers().get(2).setWorkers(indexes);

    }

    @After
    public void tearDown() {
        this.gameState = null;
        this.testPlayer = null;
        this.indexes = null;
    }


    @Test
    public void isChooseAvailableTest() throws Exception {

        //case there's an opponent worker in neighbouring tiles but it can not be moved
        assertFalse(charon.isChooseAvailable());

        //case no opponents workers in neighbouring tiles
        gameState.getIslandBoard().changePosition(gameState.getIslandBoard().getCurrentWorker(1, 1),
                new Tile.IndexTile(2, 2));
        assertFalse(charon.isChooseAvailable());

        charon.applyChoice(true);
        charon.selectWorker(testPlayer.getWorkers().get(1)); //worker in 1,2
        assertTrue(charon.isChooseAvailable());

        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().buildDome();
        assertFalse(charon.isChooseAvailable());
    }

    @Test
    public void selectOpponentsWorker() throws Exception {
        List<Tile.IndexTile> expected = new ArrayList<>();
        expected.add(new Tile.IndexTile(1, 1));
        assertEquals(0, charon.opponentsWorkerTile().size());

        charon.selectWorker(testPlayer.getWorkers().get(1));
        gameState.getIslandBoard().changePosition(charon.getWorker(), new Tile.IndexTile(2, 2));
        expected.add(new Tile.IndexTile(3, 2));
        assertEquals(charon.opponentsWorkerTile(), expected);
    }

    @Test
    public void moveWorker() throws Exception {


        Worker toMove = gameState.getPlayers().get(1).getWorkers().get(0);//worker on tile 0,1
        testPlayer.getGod().selectWorker(testPlayer.getWorkers().get(1));
        charon.moveWorker(toMove.getIndexTile());
        assertEquals(gameState.getIslandBoard().getCurrentWorker(1, 3), toMove);

        gameState.getIslandBoard().changePosition(testPlayer.getWorkers().get(0), new Tile.IndexTile(2, 2));
        charon.selectWorker(testPlayer.getWorkers().get(0));
        toMove = gameState.getPlayers().get(1).getWorkers().get(0); //the worker that now is in 1,3
        charon.moveWorker(toMove.getIndexTile());
        assertEquals(toMove, gameState.getIslandBoard().getCurrentWorker(3, 1));
    }
}