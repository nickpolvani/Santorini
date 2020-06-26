package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
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

import static org.junit.Assert.*;

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
        try {
            Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
            player1.setWorkers(workerPositions1);

            Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(1, 0), new Tile.IndexTile(0, 1)};
            player2.setWorkers(workerPositions2);

            Tile.IndexTile[] workerPositions3 = {new Tile.IndexTile(4, 4), new Tile.IndexTile(2, 1)};
            player3.setWorkers(workerPositions3);

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
        player2 = null;
        player3 = null;
        basicTurn = null;
    }


    @Test
    public void tileToMove() {
        prometheus.selectWorker(player1.getWorkers().get(0));
        assertEquals(0, prometheus.tileToMove(prometheus.getCurrentWorker().getIndexTile()).size());
        prometheus.selectWorker(player1.getWorkers().get(1));
        Collection<Tile.IndexTile> tiles = prometheus.tileToMove(prometheus.getCurrentWorker().getIndexTile());
        assertTrue(tiles.size() == 4 && tiles.contains(new Tile.IndexTile(1, 2))
                && tiles.contains(new Tile.IndexTile(0, 2)) && tiles.contains(new Tile.IndexTile(2, 2))
                && tiles.contains(new Tile.IndexTile(2, 0)));

        try {
            gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 2)).getBuilding().addBlock();
            prometheus.applyChoice(true);
            tiles = prometheus.tileToMove(prometheus.getCurrentWorker().getIndexTile());
            assertTrue(tiles.size() == 1 && tiles.contains(new Tile.IndexTile(2, 0)));
            gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().addBlock();
            gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().addBlock();
            tiles = prometheus.tileToMove(prometheus.getCurrentWorker().getIndexTile());
            assertTrue(tiles.size() == 1 && tiles.contains(new Tile.IndexTile(2, 0)));
            prometheus.applyChoice(false);
            tiles = prometheus.tileToMove(prometheus.getCurrentWorker().getIndexTile());
            assertTrue(tiles.size() == 2 && tiles.contains(new Tile.IndexTile(2, 0))
                    && tiles.contains(new Tile.IndexTile(2, 2)));
        } catch (DomeAlreadyPresentException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = Exception.class)
    public void move() throws Exception {
        prometheus.selectWorker(player1.getWorkers().get(1));

        prometheus.move(new Tile.IndexTile(1, 2));
        assertEquals(player1.getWorkers().get(1).getIndexTile(), new Tile.IndexTile(1, 2));

        prometheus.move(new Tile.IndexTile(0, 1));
    }

    @Test
    public void isChooseAvailable() throws Exception {
        //with this method we are testing checkValidOptionalBuild()

        prometheus.selectWorker(player1.getWorkers().get(0));
        gameState.getIslandBoard().changePosition(player2.getWorkers().get(0), new Tile.IndexTile(2, 3));
        gameState.getIslandBoard().changePosition(player2.getWorkers().get(1), new Tile.IndexTile(3, 4));
        assertTrue(prometheus.isChooseAvailable());
        gameState.getIslandBoard().getTile(0, 1).getBuilding().buildDome();

        //case when worker has only one tile to both move and build but the current tile of the worker is higher than the other one.
        gameState.getIslandBoard().getTile(prometheus.getCurrentWorker().getIndexTile()).setCurrentWorker(null);
        gameState.getIslandBoard().addBlock(prometheus.getCurrentWorker().getIndexTile());
        gameState.getIslandBoard().getTile(prometheus.getCurrentWorker().getIndexTile()).setCurrentWorker(prometheus.getCurrentWorker());
        assertTrue(prometheus.isChooseAvailable());

        gameState.getIslandBoard().addBlock(1, 0);
        assertFalse(prometheus.isChooseAvailable());
    }

}