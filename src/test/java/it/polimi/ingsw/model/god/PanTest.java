package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupWorkersTurn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Puoti
 */
public class PanTest {
    GameState gameState;
    Player testPlayer;
    Tile.IndexTile[] indexes;
    Tile.IndexTile newIndex;
    GameController gameController;

    @Before
    public void setUp() throws Exception {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");
        this.gameState = new GameState(players);
        this.testPlayer = this.gameState.getPlayers().get(0);
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.PAN, testPlayer));
        gameController = new GameController(gameState, null);

        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
        testPlayer.setWorkers(indexes);


        gameController.setTurn(new SetupWorkersTurn(gameController, testPlayer, new ArrayList<>()));
        gameController.setTurn(new BasicTurn(gameController, testPlayer, new ArrayList<>()));
        testPlayer.getGod().selectWorker(testPlayer.getWorkers().get(0));

    }

    @After
    public void tearDown() {
        this.gameState = null;
        this.testPlayer = null;
        this.indexes = null;
    }

    @Test
    public void move() {
        assertEquals(testPlayer.getGod().worker.getCurrentIndexTile(), indexes[0]);

        newIndex = gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getIndex();
        try {
            testPlayer.getGod().move(newIndex);
        } catch (AlreadyOccupiedException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(testPlayer.getWorkers().get(0).getCurrentIndexTile(), newIndex);

        newIndex = gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getIndex();

        try {
            testPlayer.getGod().build(newIndex);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Test passato, la mossa non era valida");
        } catch (DomeAlreadyPresentException e) {
            System.err.println(e.getMessage());
        }


    }
}