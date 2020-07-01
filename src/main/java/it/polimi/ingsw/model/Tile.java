package it.polimi.ingsw.model;


import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

import java.io.Serializable;
import java.util.Objects;

/**
 * The tile is the fundamental component of the IslandBoard.
 * It's these objects that keep the state of the IslandBoard in memory.
 * On a tile it is allowed to build a tower and place a worker.
 * If there is a worker or a dome on the tile, it's occupied.
 *
 * @see Cloneable
 * @see Serializable
 * @see IndexTile
 * @see Building
 * @see Worker
 */
public class Tile implements Cloneable, Serializable {

    /**
     * dimensions used for string representation of a Tile,
     * used in CLI
     */
    public static final int N_ROWS = 4;
    public static final int N_COLS = 7;

    /**
     * The pair of indexes of the tile on the board, it's set by the IslandBoard's constructor.
     *
     * @see IndexTile
     */
    private final IndexTile index;

    /**
     * It represent the level of the tower on the tile.
     *
     * @see Building
     */
    private final Building building;

    /**
     * The Worker instance present on the tile, null if there isn't one
     *
     * @see Worker
     */
    private Worker currentWorker;


    /**
     * Default constructor
     *
     * @param row tile row position in board
     * @param col tile column position in board
     */
    public Tile(int row, int col) {
        currentWorker = null;
        building = new Building();
        index = new IndexTile(row, col);
    }

    private Tile(IndexTile indexTile, Building building, Worker worker) {
        currentWorker = worker;
        this.building = building;
        this.index = indexTile;
    }

    /**
     * @return Return a IndexTile containing tile's index in the board.
     * @see IndexTile
     */
    public IndexTile getIndex() {
        return index;
    }

    /**
     * @return Return a int that represent the level of the build on the tile.
     * @see Building
     */
    public int getBuildingLevel() {
        return building.getLevel().getLevelInt();
    }

    /**
     * @return Return worker's instance currently on the tile or null if it's free.
     */
    public Worker getCurrentWorker() {
        return currentWorker;
    }

    /**
     * Set the current worker on the tile, could change the return of isOccupied().
     *
     * @param worker Worker's instance to be placed on the tile or 'null' to free the tile.
     * @throws AlreadyOccupiedException Throws if the currentWorker and the parameter worker are not null.
     * @see Worker
     */
    public void setCurrentWorker(Worker worker) throws AlreadyOccupiedException {
        if (worker != null && currentWorker != null) throw new AlreadyOccupiedException();
        this.currentWorker = worker;
    }

    /**
     * @return Return a building that represent the tower on the tile.
     * @see Building
     */
    public Building getBuilding() {
        return building;
    }


    /**
     * To know if the tile is free or occupied. A tile is occupied if it's present a worker or a dome on it.
     *
     * @return Returns true if there is worker or dome on the tile, false otherwise.
     */
    public boolean isOccupied() {
        return this.currentWorker != null || building.getDome();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return index.equals(tile.index) &&
                building.equals(tile.building) &&
                Objects.equals(currentWorker, tile.currentWorker);
    }

    @Override
    public Tile clone() {
        return new Tile(this.index.clone(), this.building.clone(), this.currentWorker != null ? this.currentWorker.clone() : null);
    }

    @Override
    public final String toString() {

        char[] firstLine = ("+-----+").toCharArray();
        char[] secondLine;
        char[] thirdLine;
        if (building.getDome()) {
            secondLine = ("| / \\ |").toCharArray();
            thirdLine = ("| \\ / |").toCharArray();
        } else {
            secondLine = ("| " + "L " + getBuildingLevel() + " |").toCharArray();
            thirdLine = ("| " + (currentWorker != null ? ("w " + currentWorker.getColor().getNum()) : "   ") + " |").toCharArray();
        }
        char[] fourthLine = ("+-----+").toCharArray();

        char[][] tileAsMatrix = new char[][]{firstLine, secondLine, thirdLine, fourthLine};

        StringBuilder rep = new StringBuilder((N_ROWS * (N_COLS + 1)));
        for (char[] line : tileAsMatrix) {
            rep.append(line).append('\n');
        }
        return rep.toString();
    }


    @Override
    public int hashCode() {
        return Objects.hash(index, building, currentWorker);
    }

    /**
     * Class used to model the tower.
     * There are two internal variables one to keep
     * the height of the tower in memory the other to know if there is a dome.
     * The complete towers consist of three blocks and a dome.
     */
    public static class Building implements Serializable, Cloneable {

        /**
         * Used to set the of tower's height.
         */
        private BlockLevel level;

        /**
         * Used to set if is present a dome.
         */
        private boolean dome;

        /**
         * Default constructor
         */
        Building() {
            level = BlockLevel.GROUND;
            dome = false;
        }

        Building(BlockLevel blockLevel, boolean dome) {
            this.level = blockLevel;
            this.dome = dome;
        }

        /**
         * @return Returns a BlockLevel that indicates the level of the tower.
         */
        public BlockLevel getLevel() {
            return level;
        }

        /**
         * Method used to build a new blockLevel
         *
         * @throws DomeAlreadyPresentException if this tile contains a Dome
         */
        public void addBlock() throws DomeAlreadyPresentException {
            if (this.level != BlockLevel.THREE) {
                this.level = this.level.nextLevel();
            } else {
                buildDome();
            }
        }

        //TODO write the test
        public void removeBlock() throws DomeAlreadyPresentException {
            if (this.dome) throw new DomeAlreadyPresentException();
            this.level = this.level.previousLevel();
        }

        /**
         * Used to know if there is a dome on the tower.
         *
         * @return Returns a boolean true if there is false otherwise.
         */
        public boolean getDome() {
            return dome;
        }

        /**
         * Used to set if the dome is present, true if is present false otherwise.
         *
         * @throws DomeAlreadyPresentException if this tile contains a Dome
         */
        public void buildDome() throws DomeAlreadyPresentException {
            if (this.dome) throw new DomeAlreadyPresentException();
            this.dome = true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Building building = (Building) o;
            return level == building.level &&
                    dome == building.dome;
        }

        @Override
        public int hashCode() {
            return Objects.hash(level, dome);
        }

        @Override
        public Building clone() {
            return new Building(this.level, this.dome);
        }
    }

    /**
     * Class used to model the index integer pair.
     * There are two final variables one for row and one for column.
     */
    public static class IndexTile implements Serializable, Cloneable {
        final int row;
        final int col;

        /**
         * Default constructor
         * @param c column position in board
         * @param r row position in board
         */
        public IndexTile(int r, int c) {
            row = r;
            col = c;
        }

        /**
         * @return Returns the row index of the tile.
         */
        public int getRow() {
            return row;
        }

        /**
         * @return Returns the column index of the tile.
         */
        public int getCol() {
            return col;
        }

        /*I've had to override this method because, otherwise, we could not check if two different instances
        oh IndexTile with the same column and row are equals. In fact, default method of class object checks if
        references are equal each other, not the properties of the instances.*/

        /**
         * @param obj The object's instance to check if it's equals to this
         * @return boolean Returns a boolean that it's TRUE if obj is equals to this, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof IndexTile)) return false;
            IndexTile otherIndex = (IndexTile) obj;
            return this.col == otherIndex.getCol() && this.row == otherIndex.getRow();
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override
        public IndexTile clone() {
            return new IndexTile(this.row, this.col);
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }


}