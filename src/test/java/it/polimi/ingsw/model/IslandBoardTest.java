package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;


public class IslandBoardTest {

    IslandBoard islandBoard;


    @Before
    public void setUp() {
        islandBoard = new IslandBoard();
    }

    @Test
    public void correctSetup() {
        for (int r = 0; r < IslandBoard.N_ROWS; r++) {
            for (int c = 0; c < IslandBoard.N_COLS; c++) {
                assertNotNull("Board contains null pointer", islandBoard.getBoard()[r][c]);
            }
        }
    }

    @Test
    public void neighbouringTiles() {
        /*The four corners of the board*/
        assertEquals("The method returns an incorrect number of elements - 1", 3, islandBoard.indexOfNeighbouringTiles((islandBoard.getBoard()[0][0]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 2", 3, islandBoard.indexOfNeighbouringTiles((islandBoard.getBoard()[0][IslandBoard.N_COLS - 1]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 3", 3, islandBoard.indexOfNeighbouringTiles((islandBoard.getBoard()[IslandBoard.N_ROWS - 1][0]).getIndex()).size());
        assertEquals("The method returns an incorrect number of elements - 4", 3, islandBoard.indexOfNeighbouringTiles((islandBoard.getBoard()[IslandBoard.N_ROWS - 1][IslandBoard.N_COLS - 1]).getIndex()).size());

        if (IslandBoard.N_ROWS >= 3 && IslandBoard.N_COLS >= 3) {
            assertEquals("The method returns an incorrect number of elements", 8, islandBoard.indexOfNeighbouringTiles((islandBoard.getBoard()[1][1]).getIndex()).size());
        }
    }

    @Test
    public void getTile() {
        Tile t = islandBoard.getBoard()[0][0];
        assertEquals(islandBoard.getBoard()[0][0], islandBoard.getTile(new Tile.IndexTile(0, 0)));
    }

    @Test
    public void changePosition() throws AlreadyOccupiedException {
        Worker w = new Worker(new Tile.IndexTile(0, 0), Color.BLUE);
        islandBoard.getTile(new Tile.IndexTile(0, 0)).setCurrentWorker(w);
        islandBoard.changePosition(w, new Tile.IndexTile(1, 1));
        assertEquals(w, islandBoard.getTile(new Tile.IndexTile(1, 1)).getCurrentWorker());
        assertNull(islandBoard.getTile(new Tile.IndexTile(0, 0)).getCurrentWorker());
        assertEquals(new Tile.IndexTile(1, 1), w.getIndexTile());
        try {
            Worker w1 = new Worker(new Tile.IndexTile(1, 1), Color.RED);
            islandBoard.getTile(new Tile.IndexTile(1, 1)).setCurrentWorker(w1);
            fail();
        } catch (AlreadyOccupiedException ignored) {
        }
    }

    @Test
    public void cloneTest() {
        IslandBoard boardClone = islandBoard.clone();
        for (int i = 0; i < IslandBoard.N_COLS; i++) {
            for (int j = 0; j < IslandBoard.N_ROWS; j++) {
                assertEquals(islandBoard.getBoard()[j][i], boardClone.getBoard()[j][i]);
                assertFalse(islandBoard.getBoard()[j][i] == boardClone.getBoard()[j][i]);
            }
        }
    }

    @Test
    public void tilesAreFree() throws AlreadyOccupiedException {
        islandBoard.getTile(0, 0).setCurrentWorker(new Worker(new Tile.IndexTile(0, 0), Color.RED));
        islandBoard.getTile(1, 1).setCurrentWorker(new Worker(new Tile.IndexTile(1, 1), Color.BLUE));
        assertTrue(islandBoard.tilesAreFree(new ArrayList<>(Arrays.asList(new Tile.IndexTile(1, 2), new Tile.IndexTile(2, 2), new Tile.IndexTile(4, 2)))));
        assertFalse(islandBoard.tilesAreFree(new ArrayList<>(Arrays.asList(new Tile.IndexTile(1, 1), new Tile.IndexTile(2, 2), new Tile.IndexTile(4, 2)))));

    }
}