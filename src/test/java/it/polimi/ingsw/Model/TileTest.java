package it.polimi.ingsw.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TileTest {

    private Tile tile;

    int rowIndex;
    int columnIndex;

    @Before
    @After
    public void setUp() {
        if (tile == null) tile = new Tile(0, 0);
        else {
            tile.setCurrentWorker(null);
            tile.getBuilding().setLevel(BlockLevel.GROUND);
            tile.getBuilding().setDome(false);
        }
    }

    @Test
    public void correctSetup() {
        assertNull("Worker not null after constructor", tile.getCurrentWorker());
        assertEquals("Error setup of tile's rowIndex", rowIndex, tile.getIndex().getRow());
        assertEquals("Error setup of tile's colIndex", columnIndex, tile.getIndex().getCol());
        assertEquals("blockLevel not GROUND after constructor", BlockLevel.GROUND, tile.getBuilding().getLevel());
        assertFalse("Dome's flag not null after constructor", tile.getBuilding().getDome());
    }

    @Test
    public void isOccupied() {
        assertFalse(tile.isOccupied());

        tile.setCurrentWorker(new Worker());
        assertTrue(tile.isOccupied());

        tile.setCurrentWorker(null);
        assertFalse(tile.isOccupied());

        tile.getBuilding().setDome(true);
        assertTrue(tile.isOccupied());

        tile.getBuilding().setDome(false);
        assertFalse(tile.isOccupied());
    }


}
