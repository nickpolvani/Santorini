package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HestiaTest {
    GameState gameState;
    GameController gameController;
    Player player1;
    BasicTurn basicTurn;
    God hestia;


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
        player1.setGod(godsFactory.getGod(GodDescription.HESTIA, player1));
        hestia = player1.getGod();

        // Setup Worker
        try {
            Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
            player1.setWorkers(workerPositions1);
        } catch (AlreadySetException e) {
            e.printStackTrace();
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
    public void isChooseAvailableTest() {
        hestia.selectWorker(player1.getWorkers().get(0));
        assertFalse(hestia.isChooseAvailable());
        hestia.selectWorker(player1.getWorkers().get(1));
        assertTrue(hestia.isChooseAvailable());
    }
}