package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MedusaTest {
    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;
    BasicTurn basicTurn;
    God medusa;


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
        player1.setGod(godsFactory.getGod(GodDescription.MEDUSA, player1));
        medusa = player1.getGod();

        // Setup Worker

        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(1, 0), new Tile.IndexTile(1, 3)};
        Tile.IndexTile[] workerPositions3 = {new Tile.IndexTile(4, 4), new Tile.IndexTile(2, 1)};
        try {
            gameState.getIslandBoard().addBlock(workerPositions1[0]); //Building a block under worker[0] of medusa in order to use Medusa Power
            player1.setWorkers(workerPositions1);
            player2.setWorkers(workerPositions2);
            player3.setWorkers(workerPositions3);
        } catch (Exception e) {
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
        player2 = null;
        player3 = null;
        basicTurn = null;
    }

    @Test
    public void tileToBuild() {
        medusa.selectWorker(player1.getWorkers().get(0));
        Collection<Tile.IndexTile> expectedTiles = new ArrayList<>(
                Arrays.asList(
                        new Tile.IndexTile(1, 0),
                        new Tile.IndexTile(0, 1)
                ));
        Collection<Tile.IndexTile> tileToBuild = medusa.tileToBuild(medusa.currentWorker.getIndexTile());
        assertEquals(expectedTiles, tileToBuild);

        medusa.selectWorker(player1.getWorkers().get(1));
        expectedTiles = new ArrayList<>(Arrays.asList(
                new Tile.IndexTile(0, 1),
                new Tile.IndexTile(0, 2),
                new Tile.IndexTile(1, 2),
                new Tile.IndexTile(2, 2),
                new Tile.IndexTile(2, 0)
        ));
        tileToBuild = medusa.tileToBuild(medusa.currentWorker.getIndexTile());
        assertTrue((expectedTiles.size() == tileToBuild.size()) && tileToBuild.containsAll(expectedTiles));
    }

    @Test
    public void build() throws Exception {
        medusa.selectWorker(player1.getWorkers().get(0));
        Collection<Tile.IndexTile> expectedTiles = new ArrayList<>(
                Arrays.asList(
                        new Tile.IndexTile(1, 0),
                        new Tile.IndexTile(0, 1)
                ));
        Collection<Tile.IndexTile> tileToBuild = medusa.tileToBuild(medusa.currentWorker.getIndexTile());
        assertEquals(expectedTiles, tileToBuild);
        medusa.build(new Tile.IndexTile(1, 0));
        assertEquals(1, player2.getWorkers().size());
    }
}