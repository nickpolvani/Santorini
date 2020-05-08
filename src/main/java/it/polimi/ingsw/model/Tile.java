package it.polimi.ingsw.model;


import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

import java.util.Objects;

/**
 * The tile is the fundamental component of the IslandBoard.
 * It's these objects that keep the state of IslandBoard in memory.
 * On a tile it is allowed to build a tower and place a worker.
 * If there is a worker or a dome on the tile, it's occupied.
 *
 * @author Juri Sacchetta
 */
public class Tile implements Cloneable {

    /**
     * The tile's index pair in the board
     */
    private final IndexTile index;
    /**
     * MessageType used to define the height of the tower
     */
    private final Building building;
    /**
     * Worker instance present on the tile
     */
    private Worker currentWorker;


    /**
     * Default constructor
     */
    public Tile(int row, int col) {
        currentWorker = null;
        building = new Building();
        index = new IndexTile(row, col);
    }

    private Tile(IndexTile index, Building building) {
        this.currentWorker = null;
        this.building = building;
        this.index = index;
    }

    /**
     * @return Return a IndexTile containing tile's index in the board
     */
    public IndexTile getIndex() {
        return index;
    }

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
     * @param w Worker's instance to be placed on the tile or 'null' to free the tile.
     */
    public void setCurrentWorker(Worker w) throws AlreadyOccupiedException {
        if (w != null && currentWorker != null) throw new AlreadyOccupiedException();
        this.currentWorker = w;
    }

    /**
     * @return Returns the building's instance on the tile.
     */
    public Building getBuilding() {
        return building;
    }


    /**
     * To know if the tile is free or occupied. A tile is occupied if it's present a worker or a dome on it.
     *
     * @return Returns true if there is worker or dome on the tile.
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
        Tile clone = new Tile(this.index.clone(), this.building.clone());
        if (this.currentWorker != null) {
            try {
                clone.setCurrentWorker(this.currentWorker.clone());
            } catch (AlreadyOccupiedException e) {
                e.printStackTrace();
            }
        }
        return clone;
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
    public static class Building {

        /**
         * Used to set the of tower's height.
         */
        private BlockLevel level;

        /**
         * Used to set if is present a dome.
         */
        private Boolean dome;

        /**
         * Default constructor
         */
        private Building() {
            level = BlockLevel.GROUND;
            dome = false;
        }

        private Building(BlockLevel level, Boolean dome) {
            this.level = level;
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
         */
        public void addBlock() throws DomeAlreadyPresentException {
            if (this.level != BlockLevel.THREE) {
                this.level = this.level.nextLevel();
            } else {
                buildDome();
            }
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
                    dome.equals(building.dome);
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
    public static class IndexTile {
        final int row, col;

        /**
         * Default constructor
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
         * @return Returns the column inde of the tile.
         */
        public int getCol() {
            return col;
        }

        /**
         * I've had to override this method because, otherwise, we could not check if two different instances
         * oh IndexTile with the same column and row are equals. In fact, default method of class object checks if
         * references are equal each other, not the properties of the instances.
         *
         * @param o
         * @return boolean
         */
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IndexTile)) return false;
            IndexTile otherIndex = (IndexTile) o;
            return this.col == otherIndex.getCol() && this.row == otherIndex.getRow();
        }

        @Override
        public IndexTile clone() {
            return new IndexTile(this.row, this.col);
        }
    }


}