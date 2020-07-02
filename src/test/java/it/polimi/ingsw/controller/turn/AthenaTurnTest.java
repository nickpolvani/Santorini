package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Athena;
import it.polimi.ingsw.model.god.GodDescription;
import it.polimi.ingsw.model.god.GodsFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class AthenaTurnTest {

    Player player1;
    Player player2;
    Player player3;
    private AthenaTurn athenaTurn;
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
        player1.setGod(godsFactory.getGod(GodDescription.ATHENA, player1));
        player2.setGod(godsFactory.getGod(GodDescription.ARTEMIS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.MINOTAUR, player3));

        // setup Workers
        Tile.IndexTile[] workerPositions0 = {new Tile.IndexTile(0, 0), new Tile.IndexTile(1, 1)};
        player1.setWorkers(workerPositions0);

        Tile.IndexTile[] workerPositions1 = {new Tile.IndexTile(2, 2), new Tile.IndexTile(3, 3)};
        player2.setWorkers(workerPositions1);

        Tile.IndexTile[] workerPositions2 = {new Tile.IndexTile(4, 0), new Tile.IndexTile(2, 1)};
        player3.setWorkers(workerPositions2);

        athenaTurn = new AthenaTurn(gameController, gameState.getPlayers().get(0), new ArrayList<>());


        gameController.setTurn(new SetupWorkersTurn(gameController, player1, new ArrayList<>()));
        gameController.setTurn(athenaTurn);
    }

    @After
    public void tearDown() {

        athenaTurn = null;
        gameState = null;
        players = null;
        gameController = null;

    }

    @Test
    public void switchTurnTest() throws DomeAlreadyPresentException {

        assertEquals(athenaTurn.currentPlayer, player1);
        athenaTurn.switchTurn();
        assertEquals(athenaTurn.currentPlayer, player2);
        athenaTurn.switchTurn();
        assertEquals(athenaTurn.currentPlayer, player3);
        athenaTurn.switchTurn();
        assertEquals(athenaTurn.currentPlayer, player1);

        //testing the looser's check with player2
        //building boundary domes
        gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 2)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 3)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 3)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 4)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(3, 1)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(3, 2)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(3, 4)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 2)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 3)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 4)).getBuilding().buildDome();

        athenaTurn.switchTurn();
        //this row launch a nullPointerException because when the turn notifies the controller that a player lost,
        // the controller tries to close the lobby but the lobby's reference is null

    }


    @Test
    public void athenaTileToMoveTest() throws Exception {
        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 1)).getBuilding().addBlock();
        athenaTurn.getCurrentPlayer().getGod().selectWorker(athenaTurn.getCurrentPlayer().getWorkers().get(0).getIndexTile());

        //move of the worker of the player with athena
        athenaTurn.currentPlayer.getGod().move(new Tile.IndexTile(0, 1));
        assertFalse(((Athena) player1.getGod()).getCanMoveUp());

        /*checking when in the tile neighbouring the current worker there are both a tile with higher level
        a tile with lower level*/
        gameState.getIslandBoard().getTile(2, 2).setCurrentWorker(null);
        gameState.getIslandBoard().getTile(2, 2).getBuilding().addBlock();
        gameState.getIslandBoard().getTile(2, 2).setCurrentWorker(player2.getWorkers().get(0));
        Tile.IndexTile tile1 = new Tile.IndexTile(1, 2);
        Tile.IndexTile tile2 = new Tile.IndexTile(1, 3);
        gameState.getIslandBoard().getTile(tile1).getBuilding().addBlock();
        gameState.getIslandBoard().getTile(tile1).getBuilding().addBlock();
        gameState.getIslandBoard().getTile(tile2).getBuilding().addBlock();
        Collection<Tile.IndexTile> foundTiles = athenaTurn.athenaTileToMove(player2.getWorkers().get(0));
        assertTrue(!foundTiles.contains(tile1) && foundTiles.contains(tile2));
    }

    @Test
    public void endCurrentOperationTest() throws Exception {
        assertEquals(athenaTurn.currentPlayer, player1);

        athenaTurn.switchTurn(); //We want to play with Artemis to check some behaviors of AthenaTurn
        assertEquals(athenaTurn.currentPlayer, player2);

        athenaTurn.currentPlayer.getGod().selectWorker(athenaTurn.currentPlayer.getWorkers().get(0).getIndexTile());
        athenaTurn.endCurrentOperation();
        assertEquals(athenaTurn.getCurrentOperation(), Operation.MOVE);
        athenaTurn.endCurrentOperation();
        assertEquals(athenaTurn.getCurrentOperation(), Operation.CHOOSE);
        athenaTurn.currentPlayer.getGod().applyChoice(true);
        athenaTurn.endCurrentOperation();
        assertEquals(athenaTurn.turnOperations, new LinkedList<>(Arrays.asList(Operation.MOVE, Operation.BUILD)));

        //now we want to check the if statement (so currentGod=Artemis) when current operation is a Operation.Choose
        athenaTurn.switchTurn();
        athenaTurn.switchTurn();
        assertEquals(athenaTurn.currentPlayer, player1);
        assertTrue(((Athena) player1.getGod()).getCanMoveUp());
        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 1)).getBuilding().addBlock();
        athenaTurn.getCurrentPlayer().getGod().selectWorker(athenaTurn.getCurrentPlayer().getWorkers().get(0).getIndexTile());
        athenaTurn.currentPlayer.getGod().move(new Tile.IndexTile(0, 1));

        athenaTurn.switchTurn();
        assertEquals(athenaTurn.currentPlayer, player2);

        athenaTurn.currentPlayer.getGod().selectWorker(athenaTurn.currentPlayer.getWorkers().get(0).getIndexTile());
        athenaTurn.endCurrentOperation();
        assertEquals(athenaTurn.getCurrentOperation(), Operation.MOVE);
        athenaTurn.currentPlayer.getGod().move(new Tile.IndexTile(3, 2));
        athenaTurn.endCurrentOperation();

        //case in which current worker cannot perform his additional move, because of the board state
        gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 2)).getBuilding().addBlock();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(2, 3)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(3, 1)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 1)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 2)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(4, 3)).getBuilding().buildDome();

        assertEquals(athenaTurn.getCurrentOperation(), Operation.CHOOSE);
        athenaTurn.endCurrentOperation();
        assertEquals(athenaTurn.turnOperations, new LinkedList<>(Collections.singletonList(Operation.BUILD)));
    }

    /*
        switchTurn() has not been separately tested because in endCurrentOperation Test it is called a lot of time.
        Therefore, it's already been tested.
     */


    @Test
    public void getOptionsTest() throws Exception {
        assertEquals(athenaTurn.currentPlayer, player1);
        assertEquals(athenaTurn.getCurrentOperation(), Operation.SELECT_WORKER);

        Tile.IndexTile[] indexTiles = new Tile.IndexTile[]{player1.getWorkers().get(0).getIndexTile(), player1.getWorkers().get(1).getIndexTile()};
        Options generatedOption = athenaTurn.getOptions();
        assertEquals(generatedOption.getNickname(), player1.getNickname());
        assertTrue(((TileOptions) generatedOption).getTilesToChoose().contains(indexTiles[0]) &&
                ((TileOptions) generatedOption).getTilesToChoose().contains(indexTiles[1]) &&
                ((TileOptions) generatedOption).getTilesToChoose().size() == 2);

        gameState.getIslandBoard().getTile(new Tile.IndexTile(0, 1)).getBuilding().buildDome();
        gameState.getIslandBoard().getTile(new Tile.IndexTile(1, 0)).getBuilding().buildDome();
        generatedOption = athenaTurn.getOptions();
        assertEquals(generatedOption.getNickname(), player1.getNickname());
        assertTrue(((TileOptions) generatedOption).getTilesToChoose().contains(indexTiles[1]) &&
                ((TileOptions) generatedOption).getTilesToChoose().size() == 1);

        //CASE MOVE ALREADY TESTED IN EndCurrentOperationTest

        //CASE BUILD EQUALS TO THE ONE OF BasicTurnTest

        //CASE CHOOSE ALREADY TESTED IN EndCurrentOperationTest

    }
}