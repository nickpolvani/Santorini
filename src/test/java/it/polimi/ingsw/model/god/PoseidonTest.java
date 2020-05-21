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

import static org.junit.Assert.*;

public class PoseidonTest {


    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;
    BasicTurn basicTurn;
    God poseidon;


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
        player1.setGod(godsFactory.getGod(GodDescription.POSEIDON, player1));
        /*player2.setGod(godsFactory.getGod(GodDescription.ATLAS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.ARTEMIS, player3));*/
        poseidon = player1.getGod();

        // Setup Worker
        try {
            Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
            player1.setWorkers(workerPositions1);

            Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(3, 0), new Tile.IndexTile(2, 4)};
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
    public void isChooseAvailable() throws Exception {
        poseidon.selectWorker(player1.getWorkers().get(1));
        assertTrue(poseidon.isChooseAvailable());


        gameState.getIslandBoard().getTile(1, 0).getBuilding().buildDome();

        for (int i = 1; i < 4; i++) {
            gameState.getIslandBoard().addBlock(0, 1);
            assertTrue(poseidon.isChooseAvailable());
        }
        gameState.getIslandBoard().addBlock(0, 1);
        assertFalse(poseidon.isChooseAvailable());
    }

    @Test
    public void build() throws Exception {
        poseidon.selectWorker(player1.getWorkers().get(1));

        assertTrue(poseidon.isChooseAvailable());
        poseidon.applyChoice(true);

        ((Poseidon) poseidon).build(new Tile.IndexTile(0, 1), 3);
        assertEquals(3, gameState.getIslandBoard().getBuildingLevel(0, 1));
        try {
            ((Poseidon) poseidon).build(new Tile.IndexTile(0, 1), 2);
        } catch (Exception e) {
            assertNull(e.getMessage());
        }
        ((Poseidon) poseidon).build(new Tile.IndexTile(0, 1), 1);
        assertTrue(gameState.getIslandBoard().getDome(0, 1));
    }


}