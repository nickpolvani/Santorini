package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.BlockLevel;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.*;

public class HephaestusTest {
    GameState gameState;
    GameController gameController;
    Player player1;
    Player player2;
    Player player3;

    God hephaestus;


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
        player1.setGod(godsFactory.getGod(GodDescription.HEPHAESTUS, player1));
        player2.setGod(godsFactory.getGod(GodDescription.ATLAS, player2));
        player3.setGod(godsFactory.getGod(GodDescription.PROMETHEUS, player3));
        hephaestus = player1.getGod();

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
    public void isChooseAvailableTest() throws Exception {
        hephaestus.selectWorker(player1.getWorkers().get(1));

        Tile.IndexTile index = new Tile.IndexTile(1, 2);
        hephaestus.build(index);
        Tile t = gameState.getIslandBoard().getTile(index);
        assertEquals(t.getBuilding().getLevel(), BlockLevel.ONE);
        assertTrue(hephaestus.isChooseAvailable());

        hephaestus.resetGodState();
        hephaestus.build(index);
        assertEquals(t.getBuilding().getLevel(), BlockLevel.TWO);
        assertTrue(hephaestus.isChooseAvailable());
        hephaestus.applyChoice(true);
        assertFalse(hephaestus.isChooseAvailable());

    }

    @Test(expected = Exception.class)
    public void applyChoice() throws Exception {
        hephaestus.selectWorker(player1.getWorkers().get(1));
        hephaestus.build(new Tile.IndexTile(0, 1));
        assertTrue(hephaestus.isChooseAvailable());
        hephaestus.applyChoice(true);
        assertEquals(2, gameState.getIslandBoard().getBuildingLevel(0, 1));

        hephaestus.resetGodState();
        hephaestus.build(new Tile.IndexTile(0, 1));
        assertFalse(hephaestus.isChooseAvailable());
        hephaestus.applyChoice(true);
    }


}