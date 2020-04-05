package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class TileTest {

    private Tile tile;

    int rowIndex = 0;
    int columnIndex = 0;

    @Before
    public void setUp() {
        tile = new Tile(rowIndex, columnIndex);
    }

    @After
    public void tearDown() {
        tile = null;
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
    public void isOccupied() throws AlreadyOccupiedException, DomeAlreadyPresentException {
        assertFalse(tile.isOccupied());

        tile.setCurrentWorker(new Worker(tile.getIndex(), Color.RED));
        assertTrue(tile.isOccupied());

        tile.setCurrentWorker(null);
        assertFalse(tile.isOccupied());

        tile.getBuilding().buildDome();
        assertTrue(tile.isOccupied());
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Tile clone = tile.clone();
        assertEquals(tile.getCurrentWorker(), clone.getCurrentWorker());
        assertEquals(tile.getIndex(), clone.getIndex());
        assertEquals(tile.getBuilding(), clone.getBuilding());
    }

    @Test
    public void addBlock() throws DomeAlreadyPresentException {
        assertEquals(BlockLevel.GROUND, tile.getBuilding().getLevel());
        tile.getBuilding().addBlock();
        assertEquals(BlockLevel.ONE, tile.getBuilding().getLevel());
        tile.getBuilding().addBlock();
        assertEquals(BlockLevel.TWO, tile.getBuilding().getLevel());
        tile.getBuilding().addBlock();
        assertEquals(BlockLevel.THREE, tile.getBuilding().getLevel());
        tile.getBuilding().addBlock();
        assertTrue(tile.getBuilding().getDome());
    }
}
