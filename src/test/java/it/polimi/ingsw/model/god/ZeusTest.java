package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.BlockLevel;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ZeusTest {
    GameState gameState;
    GameController gameController;
    Player player1;
    BasicTurn basicTurn;
    God zeus;


    @Before
    public void setUp() {
        LinkedHashSet<String> players = new LinkedHashSet<>();
        players.add("Nick");
        players.add("Juri");
        players.add("Fra");
        gameState = new GameState(players);
        gameController = new GameController(gameState, null);

        //setup Gods
        GodsFactory godsFactory = gameState.getGodsFactory();
        player1 = gameState.getPlayers().get(0);
        player1.setGod(godsFactory.getGod(GodDescription.ZEUS, player1));
        zeus = player1.getGod();

        // Setup Worker
        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        try {
            player1.setWorkers(workerPositions1);
            zeus.selectWorker(player1.getWorkers().get(0));
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        basicTurn = new BasicTurn(gameController, gameState.getPlayers().get(0), new ArrayList<>());
        gameController.setTurn(new SetupWorkersTurn(gameController, player1, new ArrayList<>()));
        gameController.setTurn(basicTurn);
    }

    @After
    public void tearDown() {
        gameState = null;
        gameController = null;
        player1 = null;
        basicTurn = null;
    }


    @Test
    public void getRemainingOperations() throws Exception {
        zeus.applyChoice(true);
        assertEquals(zeus.getRemainingOperations(), new LinkedList<>());
        zeus.resetGodState();
        assertEquals(zeus.getRemainingOperations(), new LinkedList<>(Collections.singletonList(Operation.BUILD)));
    }

    @Test(expected = IllegalStateException.class)
    public void applyChoice() throws Exception {
        assertEquals(gameState.getIslandBoard().getBuildingLevel(zeus.worker.getIndexTile()), BlockLevel.GROUND.getLevelInt());
        assertTrue(zeus.isChooseAvailable());
        zeus.applyChoice(true);
        assertEquals(gameState.getIslandBoard().getBuildingLevel(zeus.worker.getIndexTile()), BlockLevel.ONE.getLevelInt());
        gameState.getIslandBoard().addBlock(zeus.worker.getIndexTile());
        gameState.getIslandBoard().addBlock(zeus.worker.getIndexTile());
        zeus.applyChoice(true);
    }
}