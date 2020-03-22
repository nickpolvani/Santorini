package it.polimi.ingsw.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TileTest {

    private Tile tile;

    @Before
    public void setUp() {
        tile = new Tile();
    }

    @Test
    public void correctSetup() {
        assertNull("Worker not null after constructor", tile.getWorker());
        assertEquals("blockLevel not GROUND after constructor", tile.getHeight(), BLOCKLEVEL.GROUND);
        assertFalse("Dome's flag not null after constructor", tile.getDome());
    }

    @Test
    public void addBlock() {
        tile.addBlock();
        assertEquals("Error", tile.getHeight(), BLOCKLEVEL.ONE);
        tile.addBlock();
        assertEquals("Error", tile.getHeight(), BLOCKLEVEL.TWO);
        tile.addBlock();
        assertEquals("Error", tile.getHeight(), BLOCKLEVEL.TREE);
        tile.addBlock();
        try {
            tile.addBlock();
            fail("I managed to build on the dome");
        } catch (IllegalStateException e) {
            assertEquals("fail! It changed the state of the tower", tile.getHeight(), BLOCKLEVEL.DOME);
        }
    }
}
