package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

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

    private IslandBoard(Tile[][] board) {
        if (board.length != N_ROWS)
            throw new IllegalArgumentException("argument board has wrong size");
        for (int i = 0; i < N_ROWS; i++) {
            if (board[i].length != N_COLS)
                throw new IllegalArgumentException("argument board has wrong size");
        }
        this.board = board;
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

    @Override
    public IslandBoard clone() {
        Tile[][] boardClone = new Tile[N_ROWS][N_COLS];
        for (int row = 0; row < N_ROWS; row++) {
            for (int col = 0; col < N_COLS; col++) {
                boardClone[row][col] = this.board[row][col].clone();
            }
        }
        return new IslandBoard(boardClone);
    }


    // UTILITY METHODS : Following methods has been implemented to be taken care of coding clarity

    public boolean tilesAreFree(List<Tile.IndexTile> tiles) {
        return tiles.stream().noneMatch(indexTile -> getTile(indexTile).isOccupied());
    }

    public Tile getTile(Tile.IndexTile t) {
        return board[t.getRow()][t.getCol()];
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public int getBuildingLevel(int row, int col) {
        return board[row][col].getBuilding().getLevel().getLevelInt();
    }

    public int getBuildingLevel(Tile.IndexTile t) {
        return this.getBuildingLevel(t.getRow(), t.getCol());
    }

    public boolean getDome(int row, int col) {
        return this.getDome(new Tile.IndexTile(row, col));
    }

    public boolean getDome(Tile.IndexTile t) {
        return board[t.getRow()][t.getCol()].getBuilding().getDome();
    }

    public void addBlock(int row, int col) throws DomeAlreadyPresentException {
        this.addBlock(new Tile.IndexTile(row, col));
    }

    public void addBlock(Tile.IndexTile t) throws DomeAlreadyPresentException {
        board[t.getRow()][t.getCol()].getBuilding().addBlock();
    }

    public void setCurrentWorker(Worker w, int row, int col) throws AlreadyOccupiedException {
        this.setCurrentWorker(w, new Tile.IndexTile(row, col));
    }

    public void setCurrentWorker(Worker w, Tile.IndexTile t) throws AlreadyOccupiedException {
        board[t.getRow()][t.getCol()].setCurrentWorker(w);
    }

    public Worker getCurrentWorker(int row, int col) {
        return this.getCurrentWorker(new Tile.IndexTile(row, col));
    }

    public Worker getCurrentWorker(Tile.IndexTile t) {
        return board[t.getRow()][t.getCol()].getCurrentWorker();
    }

    public boolean isOccupied(int row, int col) {
        return this.isOccupied(new Tile.IndexTile(row, col));
    }

    public boolean isOccupied(Tile.IndexTile t) {
        return board[t.getRow()][t.getCol()].isOccupied();
    }
}