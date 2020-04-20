package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * IslandBoard models the game board. Above this are the workers' pawns and towers.
 *
 * @author Juri Sacchetta
 */
public class IslandBoard implements Cloneable {

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
     * @param index index of the tile from which to look for nearby ones
     * @return The return a Collection of tile's index close to the input index.
     */
    public Collection<Tile.IndexTile> indexOfNeighbouringTiles(Tile.IndexTile index) {
        Collection<Tile.IndexTile> indexTiles = new ArrayList<>();
        for (int c = index.getCol() - 1; c <= index.getCol() + 1; c++) {
            for (int r = index.getRow() - 1; r <= index.getRow() + 1; r++) {
                if (c >= 0 && c < IslandBoard.N_COLS && r >= 0 && r < IslandBoard.N_ROWS && getBoard()[r][c] != getBoard()[index.getRow()][index.getCol()])
                    indexTiles.add(new Tile.IndexTile(r, c));
            }
        }
        return indexTiles;
    }

    /**
     * @param
     * @return
     */
    public Tile getTile(Tile.IndexTile t) {
        return board[t.getRow()][t.getCol()];
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    /**
     * @param worker
     * @param indexNewPosition
     * @throws AlreadyOccupiedException
     */
    public void changePosition(Worker worker, Tile.IndexTile indexNewPosition) throws AlreadyOccupiedException {
        if (worker == null || indexNewPosition == null) throw new NullPointerException();

        this.getTile(worker.getIndexTile()).setCurrentWorker(null);
        getTile(indexNewPosition).setCurrentWorker(worker);
        worker.setIndexTile(indexNewPosition);
    }

    public boolean tilesAreFree(List<Tile.IndexTile> tiles) {
        return tiles.stream().noneMatch(indexTile -> getTile(indexTile).isOccupied());
    }

    @Override
    public Tile[][] clone() {
        return board.clone();
    }
}