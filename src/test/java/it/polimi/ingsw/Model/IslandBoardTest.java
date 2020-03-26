package it.polimi.ingsw.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class IslandBoardTest {

    IslandBoard instance;


    @Before
    @After
    public void setUp() {
        instance = new IslandBoard();
    }

    @Test
    public void correctSetup() {
        for (int r = 0; r < IslandBoard.N_ROWS; r++) {
            for (int c = 0; c < IslandBoard.N_COLS; c++) {
                assertNotNull("Board contains null pointer", instance.getBoard()[r][c]);
            }
        }
    }

    @Test
    public void neighbouringTiles() {

        try {
            instance.neighbouringTiles(null);
            fail("It is possible to pass null");
        } catch (NullPointerException e) {
            assertTrue(true);
        }

        /*The four corners of the board*/
        assertEquals("The method returns an incorrect number of elements - 1", 3, instance.neighbouringTiles(instance.getBoard()[0][0]).size());
        assertEquals("The method returns an incorrect number of elements - 2", 3, instance.neighbouringTiles(instance.getBoard()[0][IslandBoard.N_COLS - 1]).size());
        assertEquals("The method returns an incorrect number of elements - 3", 3, instance.neighbouringTiles(instance.getBoard()[IslandBoard.N_ROWS - 1][0]).size());
        assertEquals("The method returns an incorrect number of elements - 4", 3, instance.neighbouringTiles(instance.getBoard()[IslandBoard.N_ROWS - 1][IslandBoard.N_COLS - 1]).size());

        if (IslandBoard.N_ROWS >= 3 && IslandBoard.N_COLS >= 3) {
            assertEquals("The method returns an incorrect number of elements", 8, instance.neighbouringTiles(instance.getBoard()[1][1]).size());
        }
    }

    @Test
    public void changePosition() {
        Worker w1 = new Worker();
        instance.getBoard()[0][0].setCurrentWorker(w1);
        w1.setTile(instance.getBoard()[0][0]);
        assertEquals("The reference to the worker was lost", w1, instance.getBoard()[0][0].getCurrentWorker());
        assertEquals("The reference to the current tile was lost", instance.getBoard()[0][0], w1.getTile());

        instance.changePosition(w1, instance.getBoard()[IslandBoard.N_ROWS % 2][IslandBoard.N_COLS % 2]);
        assertEquals("The worker variable in tile is not update", w1, instance.getBoard()[IslandBoard.N_ROWS % 2][IslandBoard.N_COLS % 2].getCurrentWorker());
        assertEquals("The tile variable in worker is not update", instance.getBoard()[IslandBoard.N_ROWS % 2][IslandBoard.N_COLS % 2], w1.getTile());
        assertNull("The worker variable in old tile is not update", instance.getBoard()[0][0].getCurrentWorker());
    }
}