package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.GodDescription;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Francesco Puoti
 */

public class PlayerTest {

    GameState gameState;
    GameController gameController;
    Tile.IndexTile[] indexes0;

    @Before
    public void setUp() {
        Set<String> players = new LinkedHashSet<>();
        players.add("Francesco");
        players.add("Nick");
        players.add("Juri");

        gameState = new GameState(players);
        gameController = new GameController(gameState, null);

        indexes0 = new Tile.IndexTile[2];
        indexes0[0] = new Tile.IndexTile(0, 1);
        indexes0[1] = new Tile.IndexTile(1, 2);
    }

    @After
    public void tearDown() {
        gameState = null;
        gameController = null;
        indexes0 = null;
    }

    @Test
    public void constructorTest() {
        assertEquals(gameState.getPlayers().get(0).getNickname(), "Francesco");
        assertEquals(gameState.getPlayers().get(1).getNickname(), "Nick");
        assertEquals(gameState.getPlayers().get(2).getNickname(), "Juri");
    }

    @Test
    public void setWorkerTest() {
        Player testPlayer = gameState.getPlayers().get(0);
        assertNull("Workers'array should be null after constructor without any call to the setter method", testPlayer.getWorkers());

        try {
            testPlayer.setWorkers(indexes0);
        } catch (AlreadySetException e) {
            System.out.println(e.getMessage());
        }

        try {
            testPlayer.setWorkers(indexes0);
        } catch (AlreadySetException e) {
            System.out.println("Tried how the method trows exception when someone tries to set workers another time. Correct behaviour checked");
        }
    }

    @Test
    public void setGodTest() {
        Player testPlayer = gameState.getPlayers().get(0);
        assertNull("God not already set: property should be null", testPlayer.getGod());
        testPlayer.setGod(gameState.getGodsFactory().getGod(GodDescription.PAN, testPlayer));
        assertEquals(testPlayer.getGod(), gameState.getGodsFactory().getGod(GodDescription.PAN, testPlayer));
    }

    /*@Test TODO ho tolto il test perchè i player non implemantatno più serializable
    public void serialize() throws IOException, ClassNotFoundException {
        Player player = gameState.getPlayers().get(0);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.reset();
        out.writeObject(player);
        out.flush();
        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
        ObjectInputStream in = new ObjectInputStream(is);
        Player pOut = (Player) in.readObject();
        assertEquals(player.getWorkers(), pOut.getWorkers());
        assertEquals(player.getNickname(), pOut.getNickname());
    }*/
}
