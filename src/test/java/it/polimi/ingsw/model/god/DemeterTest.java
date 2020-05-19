package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DemeterTest {

    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;

    God demeter;

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
        player1.setGod(godsFactory.getGod(GodDescription.DEMETER, player1));
        player2.setGod(godsFactory.getGod(GodDescription.APOLLO, player2));
        player3.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player3));
        demeter = player1.getGod();

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

    }

    @Test
    public void tileToBuild() throws DomeAlreadyPresentException {
        demeter.selectWorker(gameState.getIslandBoard().getCurrentWorker(1, 1));
        Collection<Tile.IndexTile> indexTileCollection = demeter.tileToBuild(new Tile.IndexTile(1, 1));
        assertTrue(indexTileCollection.size() == 4
                && indexTileCollection.contains(new Tile.IndexTile(0, 2))
                && indexTileCollection.contains(new Tile.IndexTile(1, 2))
                && indexTileCollection.contains(new Tile.IndexTile(2, 2))
                && indexTileCollection.contains(new Tile.IndexTile(2, 0)));
        demeter.build(new Tile.IndexTile(0, 2));
        indexTileCollection = demeter.tileToBuild(new Tile.IndexTile(1, 1));
        assertTrue(indexTileCollection.size() == 3
                && indexTileCollection.contains(new Tile.IndexTile(1, 2))
                && indexTileCollection.contains(new Tile.IndexTile(2, 2))
                && indexTileCollection.contains(new Tile.IndexTile(2, 0)));
    }


    @Test
    public void getTurnOperations() {
        Queue<Operation> queue = new LinkedList<>(Arrays.asList(Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE));
        assertEquals(queue, demeter.getTurnOperations());
    }
}