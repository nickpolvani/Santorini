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
import it.polimi.ingsw.model.Tile.IndexTile;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.*;

/* APOLLO: Your Worker may  move into an opponent Worker’s space by forcing their Worker to
 * the space just vacated.

 */
public class ApolloTest {
    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;
    BasicTurn basicTurn;
    God apollo;


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
        player1.setGod(godsFactory.getGod(GodDescription.APOLLO, player1));
        player2.setGod(godsFactory.getGod(GodDescription.ATLAS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player3));
        apollo = player1.getGod();

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

        player1.getGod().selectWorker(player1.getWorkers().get(0));
        Tile.IndexTile i1 = new Tile.IndexTile(0, 1);
        Tile.IndexTile i2 = new Tile.IndexTile(1, 0);

        Collection<Tile.IndexTile> tileToMove = apollo.tileToMove(apollo.worker.getCurrentIndexTile());
        assertTrue(tileToMove.size() == 2 && tileToMove.contains(i1)
                && tileToMove.contains(i2));

        try {
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(2, 0)).getBuilding().buildDome();
        } catch (DomeAlreadyPresentException e) {
            e.printStackTrace();
        }

        apollo.selectWorker(player1.getWorkers().get(1));
        tileToMove = apollo.tileToMove(apollo.worker.getCurrentIndexTile());
        assertTrue(tileToMove.size() == 6 && tileToMove.contains(new IndexTile(0, 1)) &&
                tileToMove.contains(new IndexTile(1, 0))
                && tileToMove.contains(new IndexTile(2, 1))
                && tileToMove.contains(new IndexTile(2, 2))
                && tileToMove.contains(new IndexTile(1, 2))
                && tileToMove.contains(new IndexTile(0, 2)));
    }

    @Test
    public void tileToMove2() {
        apollo.selectWorker(player1.getWorkers().get(0));
        List<IndexTile> positions = new ArrayList<>();
        positions.add(new IndexTile(0, 1));
        positions.add(new IndexTile(1, 0));
        assertEquals(2, apollo.tileToMove(apollo.worker.getCurrentIndexTile()).size());
        for (IndexTile position : positions) {
            try {
                gameState.getIslandBoard().getTile(position).getBuilding().addBlock();
                gameState.getIslandBoard().getTile(position).getBuilding().addBlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        assertEquals(0, apollo.tileToMove(apollo.worker.getCurrentIndexTile()).size());
    }

    @Test
    public void tileToBuild() {
        try {
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(2, 0)).getBuilding().buildDome();
        } catch (DomeAlreadyPresentException e) {
            e.printStackTrace();
        }

        Collection<Tile.IndexTile> tileToBuild = apollo.tileToBuild(new Tile.IndexTile(1, 0));
        assertEquals(0, tileToBuild.size());

        tileToBuild = apollo.tileToBuild(new Tile.IndexTile(1, 1));
        assertTrue(tileToBuild.size() == 3 && tileToBuild.contains(new IndexTile(0, 2))
                && tileToBuild.contains(new IndexTile(1, 2))
                && tileToBuild.contains(new IndexTile(2, 2)));
    }


    @Test
    public void cannotMove() {
        assertFalse(apollo.cannotMove());
        try {
            gameController.getGameState().getIslandBoard().changePosition(player2.getWorkers().get(0), new IndexTile(4, 0));
            gameController.getGameState().getIslandBoard().changePosition(player2.getWorkers().get(1), new IndexTile(4, 1));
            gameController.getGameState().getIslandBoard().changePosition(player1.getWorkers().get(1), new IndexTile(0, 1));
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(1, 0)).getBuilding().buildDome();
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(1, 1)).getBuilding().buildDome();
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().buildDome();
            gameController.getGameState().getIslandBoard().getTile(new Tile.IndexTile(0, 2)).getBuilding().buildDome();
        } catch (DomeAlreadyPresentException | AlreadyOccupiedException e) {
            e.printStackTrace();
        }
        assertTrue(apollo.cannotMove());

    }

    @Test
    public void cannotBuild() {
        apollo.selectWorker(player1.getWorkers().get(0));
        assertTrue(apollo.cannotBuild());
        apollo.selectWorker(player1.getWorkers().get(1));
        assertFalse(apollo.cannotBuild());
    }


    @Test
    public void move() {
        apollo.selectWorker(player1.getWorkers().get(0));
        try {
            IndexTile oldPosition = apollo.worker.getCurrentIndexTile();
            IndexTile newPosition = new IndexTile(0, 1);
            Worker otherWorker = gameState.getIslandBoard().getTile(newPosition).getCurrentWorker();
            assertNotNull(otherWorker);
            apollo.move(newPosition);
            assertEquals(gameState.getIslandBoard().getTile(newPosition).getCurrentWorker(), player1.getWorkers().get(0));
            assertEquals(gameState.getIslandBoard().getTile(oldPosition).getCurrentWorker(), otherWorker);
        } catch (AlreadyOccupiedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void move2() {
        apollo.selectWorker(player1.getWorkers().get(0));
        IndexTile oldPosition = apollo.worker.getCurrentIndexTile();
        Tile oldTile = gameState.getIslandBoard().getTile(oldPosition);
        IndexTile newPosition = new IndexTile(0, 1);
        Tile newTile = gameState.getIslandBoard().getTile(newPosition);

        try {
            oldTile.getBuilding().addBlock();
            oldTile.getBuilding().addBlock();

            newTile.getBuilding().addBlock();
            newTile.getBuilding().addBlock();
            newTile.getBuilding().addBlock();
            apollo.move(newPosition);
            assertTrue(player1.isWinner());
            assertEquals(newTile.getCurrentWorker(), apollo.worker);
        } catch (DomeAlreadyPresentException | AlreadyOccupiedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void build() {
        apollo.selectWorker(player1.getWorkers().get(1));
        IndexTile buildPosition = new IndexTile(1, 2);
        Tile buildTile = gameState.getIslandBoard().getTile(buildPosition);
        assertEquals(0, buildTile.getBuildingLevel());
        try {
            apollo.build(buildPosition);
            assertEquals(1, buildTile.getBuildingLevel());
            apollo.build(buildPosition);
            assertEquals(2, buildTile.getBuildingLevel());
            apollo.build(buildPosition);
            assertEquals(3, buildTile.getBuildingLevel());
            apollo.build(buildPosition);
            assertTrue(buildTile.getBuilding().getDome());
        } catch (DomeAlreadyPresentException e) {
            e.printStackTrace();
        }
    }
}