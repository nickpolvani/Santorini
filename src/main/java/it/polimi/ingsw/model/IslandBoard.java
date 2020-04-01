package it.polimi.ingsw.model;

import it.polimi.ingsw.bean.options.BoardMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.ArrayList;
import java.util.Collection;

/**
 * IslandBoard models the game board. Above this are the workers' pawns and towers.
 *
 * @author Juri Sacchetta
 */
public class IslandBoard extends Observable<BoardMessage> implements Cloneable {

    /**
     * This constant define the rows numbers of the board
     */
    public static final int N_ROWS = 5;
    /**
     * This constant define the columns numbers of the board
     */
    public static final int N_COLS = 5;

    /**
     * it's an array of tiles that make up the board
     */
    private final Tile[][] board;


    /**
     * Default constructor
     */
    public IslandBoard() {
        this.board = new Tile[IslandBoard.N_ROWS][IslandBoard.N_COLS];
        for (int r = 0; r < IslandBoard.N_ROWS; r++) {
            for (int c = 0; c < IslandBoard.N_COLS; c++) {
                this.board[r][c] = new Tile(r, c);
            }
        }
    }

    /**
     * @return Returns the board of the model.
     */
    public Tile[][] getBoard() {
        return board;
    }

    /**
     * @param tile instance of the tile from which to look for nearby ones
     * @return The return contains the references of the tiles close to the input tile.
     */
    public Collection<Tile> neighbouringTiles(Tile tile) {
        if (tile == null) throw new NullPointerException("The tile parameter is null");
        Collection<Tile> tiles = new ArrayList<>();
        for (int c = tile.getIndex().getCol() - 1; c <= tile.getIndex().getCol() + 1; c++) {
            for (int r = tile.getIndex().getRow() - 1; r <= tile.getIndex().getRow() + 1; r++) {
                if (c >= 0 && c < IslandBoard.N_COLS && r >= 0 && r < IslandBoard.N_ROWS && getBoard()[r][c] != tile)
                    tiles.add(getBoard()[c][r]);
            }
        }
        return tiles;
    }
}