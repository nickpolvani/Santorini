package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * @author Francesco Puoti
 */
public class WorkerTest {

    Worker[] workers;
    Tile.IndexTile[] indexes;

    @Before
    public void setUp() {
        indexes = new Tile.IndexTile[2];
        indexes[0] = new Tile.IndexTile(0, 1);
        indexes[1] = new Tile.IndexTile(1, 2);
    }

    @After
    public void tearDown() {
        indexes = null;
    }

    @Test
    public void constructorTest() {
        workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker(indexes[i], Color.RED);
        }

        for (int i = 0; i < workers.length; i++) {
            assertEquals(workers[i].getIndexTile(), indexes[i]);
            assertEquals(workers[i].getColor(), Color.RED);
        }
    }

    @Test
    public void setIndexTileTest() {

        Worker testWorker = new Worker(indexes[0], Color.RED);

        indexes[0] = new Tile.IndexTile(3, 1);
        testWorker.setIndexTile(indexes[0]);

        assertEquals(testWorker.getIndexTile(), indexes[0]);
    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        Worker testWorker = new Worker(indexes[0], Color.RED);
        Worker testCloneWorker = testWorker.clone();

        assertEquals(testWorker, testCloneWorker);
        assertNotSame(testWorker, testCloneWorker);

        assertEquals(testCloneWorker.getIndexTile(), testWorker.getIndexTile());

        assertEquals(testCloneWorker.getColor(), testWorker.getColor());
    }


}
