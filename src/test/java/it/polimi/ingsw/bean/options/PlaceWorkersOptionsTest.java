package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.utilities.MessageType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PlaceWorkersOptionsTest {
    PlaceWorkersOptions placeWorkersOptions;

    @Before
    public void setUp() {
        IslandBoard board = new IslandBoard();

        List<Tile.IndexTile> tiles = new ArrayList<>();
        for (Tile[] row : board.getBoard()) {
            tiles.addAll(Arrays.stream(row).filter(x -> !x.isOccupied()).map(Tile::getIndex).collect(Collectors.toList()));
        }

        placeWorkersOptions = new PlaceWorkersOptions("Fra", tiles, new IslandBoard(),
                Operation.PLACE_WORKERS, MessageType.PLACE_WORKERS, Color.RED);
    }

    @After
    public void tearDown() {
        placeWorkersOptions = null;
    }

    @Test
    public void isValid() {
        String mockUpInput = "1    ,   1   -  2  ,     2";
        assertNull(placeWorkersOptions.isValid(mockUpInput));
        mockUpInput = "1    ,   1   -  1  ,     1";
        assertNotNull(placeWorkersOptions.isValid(mockUpInput));
        mockUpInput = "7,8-9,5";
        assertNotNull(placeWorkersOptions.isValid(mockUpInput));
        mockUpInput = "1-2,5-4";
        assertNotNull(placeWorkersOptions.isValid(mockUpInput));
    }
}