package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PrometheusTest {

    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;
    BasicTurn basicTurn;
    God prometheus;


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
        player2 = gameState.getPlayers().get(1);
        player3 = gameState.getPlayers().get(2);
        player1.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player1));
        player2.setGod(godsFactory.getGod(GodDescription.ATLAS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.ARTEMIS, player3));
        prometheus = player1.getGod();

        // Setup Worker
        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        try {
            player1.setWorkers(workerPositions1);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }
        Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(1, 0), new Tile.IndexTile(0, 1)};
        try {
            player2.setWorkers(workerPositions2);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }
        Tile.IndexTile[] workerPositions3 = {new Tile.IndexTile(4, 4), new Tile.IndexTile(2, 1)};
        try {
            player3.setWorkers(workerPositions3);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        basicTurn = new BasicTurn(gameController, gameState.getPlayers().get(0), new ArrayList<>());
        gameController.setTurn(new SetupWorkersTurn(gameController, player1, new ArrayList<>()));
        gameController.setTurn(basicTurn);
        gameController.setTurn(basicTurn);
    }

    @After
    public void tearDown() {
        gameState = null;
        gameController = null;
        player1 = null;
        player2 = null;
        player3 = null;
        basicTurn = null;
    }


    @Test
    public void tileToMove() {
        prometheus.selectWorker(player1.getWorkers().get(0));
        assertTrue(prometheus.tileToMove(prometheus.getWorker().getCurrentIndexTile()).size() == 0);
        prometheus.selectWorker(player1.getWorkers().get(1));
        Collection<Tile.IndexTile> tiles = prometheus.tileToMove(prometheus.getWorker().getCurrentIndexTile());
        assertTrue(tiles.size() == 4 && tiles.contains(new Tile.IndexTile(1, 2))
                && tiles.contains(new Tile.IndexTile(0, 2)) && tiles.contains(new Tile.IndexTile(2, 2))
                && tiles.contains(new Tile.IndexTile(2, 0)));

        try {
            gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 2)).getBuilding().addBlock();
            prometheus.applyChoice(true);
            tiles = prometheus.tileToMove(prometheus.getWorker().getCurrentIndexTile());
            assertTrue(tiles.size() == 1 && tiles.contains(new Tile.IndexTile(2, 0)));
            gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().addBlock();
            tiles = prometheus.tileToMove(prometheus.getWorker().getCurrentIndexTile());
            assertTrue(tiles.size() == 1 && tiles.contains(new Tile.IndexTile(2, 0)));
        } catch (DomeAlreadyPresentException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void move() {
        prometheus.selectWorker(player1.getWorkers().get(1));
        try {
            prometheus.move(new Tile.IndexTile(1, 2));
            assertEquals(player1.getWorkers().get(1).getCurrentIndexTile(), new Tile.IndexTile(1, 2));
        } catch (AlreadyOccupiedException e) {
            e.printStackTrace();
        }
        try {
            prometheus.move(new Tile.IndexTile(0, 1));
        } catch (Exception e) {
            System.out.println("Test passed");
        }
    }

}