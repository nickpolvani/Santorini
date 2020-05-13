package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class CLITest {

    CLI cli;

    @Before
    public void setUp() throws Exception {
        cli = new CLI();
    }

    @After
    public void tearDown() throws Exception {
        cli = null;
    }

    @Test
    public void printWelcome() {
        cli.printWelcome();
    }

    @Test
    public void printBoardTest() throws Exception {
        IslandBoard board = new IslandBoard();

        board.getTile(0, 0).setCurrentWorker(new Worker(new Tile.IndexTile(0, 0), Color.RED));
        board.getTile(1, 1).setCurrentWorker(new Worker(new Tile.IndexTile(1, 1), Color.BLUE));
        board.getTile(2, 2).setCurrentWorker(new Worker(new Tile.IndexTile(2, 2), Color.GREEN));

        board.addBlock(3, 3);
        board.addBlock(3, 3);
        board.addBlock(3, 4);
        board.getTile(4, 0).getBuilding().buildDome();
        board.getTile(3, 1).getBuilding().buildDome();

        cli.printBoard(board);
    }

    @Test
    public void testing() {

        String userInput = "1,             1-         1,          2";
        String toCheck = userInput.replace(" ", "");
        Pattern checkTile = Pattern.compile("[01234],[01234]-[01234],[01234]");
        assertTrue(checkTile.matcher(toCheck).matches());

    }


}