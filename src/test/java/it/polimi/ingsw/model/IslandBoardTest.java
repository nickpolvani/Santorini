package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
        /*The four corners of the board*/
        assertEquals("The method returns an incorrect number of elements - 1", 3, instance.indexOfNeighbouringTiles((instance.getBoard()[0][0]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 2", 3, instance.indexOfNeighbouringTiles((instance.getBoard()[0][IslandBoard.N_COLS - 1]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 3", 3, instance.indexOfNeighbouringTiles((instance.getBoard()[IslandBoard.N_ROWS - 1][0]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 4", 3, instance.indexOfNeighbouringTiles((instance.getBoard()[IslandBoard.N_ROWS - 1][IslandBoard.N_COLS - 1]).getIndex()).size());

        if (IslandBoard.N_ROWS >= 3 && IslandBoard.N_COLS >= 3) {
            assertEquals("The method returns an incorrect number of elements", 8, instance.indexOfNeighbouringTiles((instance.getBoard()[1][1]).getIndex()).size());
        }
    }
}