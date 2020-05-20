package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.model.god.GodsFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BasicTurnTest {

    Player player1;
    Player player2;
    Player player3;
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
        gameController = new GameController(gameState, null);

        //setup Gods
        GodsFactory godsFactory = gameState.getGodsFactory();
        player1 = gameState.getPlayers().get(0);
        player2 = gameState.getPlayers().get(1);
        player3 = gameState.getPlayers().get(2);
        player1.setGod(godsFactory.getGod(GodDescription.APOLLO, player1));
        player2.setGod(godsFactory.getGod(GodDescription.ATLAS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player3));

        // setup Workers
        Tile.IndexTile[] workerPositions0 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        player1.setWorkers(workerPositions0);

        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(2, 2), new Tile.IndexTile(3, 3)};
        player2.setWorkers(workerPositions1);

        Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(4, 4), new Tile.IndexTile(2, 1)};
        player3.setWorkers(workerPositions2);

        basicTurn = new BasicTurn(gameController, gameState.getPlayers().get(0), new ArrayList<>());


        gameController.setTurn(new SetupWorkersTurn(gameController, player1, new ArrayList<>()));
        gameController.setTurn(basicTurn);
        gameController.setTurn(basicTurn);


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
        assertEquals(basicTurn.getCurrentPlayer(), player1);
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), player2);
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), player3);
        basicTurn.switchTurn();
        assertEquals(basicTurn.getCurrentPlayer(), player1);
    }

    @Test
    public void endCurrentOperationTest() {
        assertEquals(basicTurn.getCurrentPlayer(), player1);
        assertEquals(basicTurn.getCurrentOperation(), Operation.SELECT_WORKER);
        player1.getGod().selectWorker(player1.getWorkers().get(0).getIndexTile());
        basicTurn.endCurrentOperation();
        assertEquals(basicTurn.getCurrentPlayer(), player1);
        assertEquals(basicTurn.getCurrentOperation(), Operation.MOVE);
        basicTurn.endCurrentOperation();
        assertEquals(basicTurn.getCurrentPlayer(), player1);
        assertEquals(basicTurn.getCurrentOperation(), Operation.BUILD);
        basicTurn.endCurrentOperation();
        assertEquals(basicTurn.getCurrentPlayer(), player2);
        assertEquals(basicTurn.getCurrentOperation(), Operation.SELECT_WORKER);

        //testing endCurrentOperation handleRemainingOption is tested too
    }
}