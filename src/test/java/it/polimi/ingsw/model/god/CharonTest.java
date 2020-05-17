package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void getRemainingOperations() throws Exception {
        charon.applyChoice(false);
        Operation[] operationsArray = {Operation.MOVE, Operation.BUILD};
        assertEquals(charon.getRemainingOperations(), new LinkedList<>(Arrays.asList(operationsArray)));

        charon.applyChoice(true);
        operationsArray = new Operation[]{Operation.SELECT_OPPONENTS_WORKER, Operation.MESSAGE_NO_REPLY,
                Operation.MOVE, Operation.BUILD};
        assertEquals(charon.getRemainingOperations(), new LinkedList<>(Arrays.asList(operationsArray)));

        //case no opponents workers in neighbouring tiles
        gameState.getIslandBoard().changePosition(gameState.getIslandBoard().getCurrentWorker(1, 1),
                new Tile.IndexTile(2, 0));
        charon.applyChoice(true);
        operationsArray = new Operation[]{Operation.MESSAGE_NO_REPLY, Operation.MOVE, Operation.BUILD};
        assertEquals(charon.getRemainingOperations(), new LinkedList<>(Arrays.asList(operationsArray)));

    }

    @Test
    public void selectOpponentsWorker() throws Exception {
        List<Tile.IndexTile> expected = new ArrayList<>();
        expected.add(new Tile.IndexTile(1, 1));
        assertEquals(charon.selectOpponentsWorker(), expected);

        charon.selectWorker(testPlayer.getWorkers()[1]);
        gameState.getIslandBoard().changePosition(charon.getWorker(), new Tile.IndexTile(2, 2));
        expected.add(new Tile.IndexTile(3, 2));
        assertEquals(charon.selectOpponentsWorker(), expected);
    }

    @Test
    public void moveWorker() throws Exception {

        Worker toMove = gameState.getPlayers().get(1).getWorkers()[0];//worker on tile 0,1
        charon.moveWorker(toMove.getIndexTile());
        assertTrue(charon.getChoiceNotAllowedMessage().contains("Sorry but you can't move the worker"));

        testPlayer.getGod().selectWorker(testPlayer.getWorkers()[1]);
        charon.moveWorker(toMove.getIndexTile());
        assertEquals(gameState.getIslandBoard().getCurrentWorker(1, 3), toMove);

        gameState.getIslandBoard().changePosition(testPlayer.getWorkers()[0], new Tile.IndexTile(2, 2));
        charon.selectWorker(testPlayer.getWorkers()[0]);
        toMove = gameState.getPlayers().get(1).getWorkers()[1]; //the worker in 3,2
        charon.moveWorker((toMove.getIndexTile()));
        assertTrue(charon.getChoiceNotAllowedMessage().contains("Sorry but you can't move the worker"));

        toMove = gameState.getPlayers().get(1).getWorkers()[0]; //the worker that now is in 1,3
        charon.moveWorker(toMove.getIndexTile());
        assertEquals(toMove, gameState.getIslandBoard().getCurrentWorker(3, 1));
    }
}