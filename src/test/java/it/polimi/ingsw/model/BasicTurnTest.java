package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.model.god.GodNameAndDescription;
import it.polimi.ingsw.model.god.GodsFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BasicTurnTest {

    private BasicTurn basicTurn;
    private GameState gameState;
    private Set<String> players;
    private GameController gameController;


    @Before
    public void setUp() throws Exception {
        players = new LinkedHashSet<>();
        players.add("Nick");
        players.add("Juri");
        players.add("Fra");
        gameState = new GameState(players);
        gameController = new GameController(gameState);

        //setup Gods
        GodsFactory godsFactory = gameState.getGodsFactory();
        // Pan is the only God we've implemented yet
        gameState.getPlayers().get(0).setGod(godsFactory.getGod(GodNameAndDescription.PAN));
        gameState.getPlayers().get(1).setGod(godsFactory.getGod(GodNameAndDescription.PAN));
        gameState.getPlayers().get(2).setGod(godsFactory.getGod(GodNameAndDescription.PAN));

        // setup Workers

        Tile.IndexTile[] workerPositions0 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        gameState.getPlayers().get(0).setWorker(Color.RED, workerPositions0);

        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(2, 2), new Tile.IndexTile(3, 3)};
        gameState.getPlayers().get(1).setWorker(Color.RED, workerPositions1);

        Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(4, 4), new Tile.IndexTile(2, 1)};
        gameState.getPlayers().get(2).setWorker(Color.RED, workerPositions2);

        basicTurn = new BasicTurn(gameState, gameState.getPlayers().get(0));
        gameState.setTurn(basicTurn);


    }

    @After
    public void tearDown() throws Exception {
        basicTurn = null;
        gameState = null;
        players = null;
        gameController = null;
    }


    @Test
    public void switchTurnTest() {
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(0));
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(1));
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(2));
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(0));
    }

    @Test
    public void endCurrentOperationTest() {
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(0));
        assertEquals(basicTurn.getCurrentOperation(), Operation.SELECT_WORKER);
        gameState.getPlayers().get(0).getGod().selectWorker(gameState.getPlayers().get(0).getWorker()[0]); // calls endCurrentOperation
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(0));
        assertEquals(basicTurn.getCurrentOperation(), Operation.MOVE);
        basicTurn.endCurrentOperation();
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(0));
        assertEquals(basicTurn.getCurrentOperation(), Operation.BUILD);
        basicTurn.endCurrentOperation();
        assertEquals(basicTurn.getCurrentPlayer(), gameState.getPlayers().get(1));
        assertEquals(basicTurn.getCurrentOperation(), Operation.SELECT_WORKER);

    }
}