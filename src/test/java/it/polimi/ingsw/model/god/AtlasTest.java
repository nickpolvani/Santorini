package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.BlockLevel;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class AtlasTest {

    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;

    God atlas;

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
        player1.setGod(godsFactory.getGod(GodDescription.ATLAS, player1));
        player2.setGod(godsFactory.getGod(GodDescription.APOLLO, player2));
        player3.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player3));
        atlas = player1.getGod();

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

    @After
    public void tearDown() {
        gameState = null;
        gameController = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    @Test
    public void build() throws DomeAlreadyPresentException {
        atlas.selectWorker(player1.getWorkers().get(1));
        atlas.applyChoice(true);
        atlas.build(new Tile.IndexTile(1, 2));
        assertTrue(gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().getDome());
        atlas.resetGodState();
        atlas.build(new Tile.IndexTile(0, 2));
        assertSame(gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().getLevel(), BlockLevel.ONE);
    }
}