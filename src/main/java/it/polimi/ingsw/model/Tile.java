package it.polimi.ingsw.model;


import it.polimi.ingsw.exception.AlreadyOccupiedException;

/**
 * The tile is the fundamental component of the IslandBoard.
 * It's these objects that keep the state of IslandBoard in memory.
 * On a tile it is allowed to build a tower and place a worker.
 * If there is a worker or a dome on the tile, it's occupied.
 *
 * @author Juri Sacchetta
 */
public class Tile {

    /**
     * The tile's index pair in the board
     */
    private final IndexTile index;
    /**
     * Enum used to define the height of the tower
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

    /**
     * @return Return a IndexTile containing tile's index in the board
     */
    public IndexTile getIndex() {
        return index;
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
    public Boolean isOccupied() {
        return this.currentWorker != null || building.getDome();
    }

    @Override
    public Tile clone() {
        Tile clone = new Tile(this.getIndex().getRow(), this.getIndex().getCol());
        try {
            clone.setCurrentWorker(this.currentWorker);
        } catch (AlreadyOccupiedException e) {
            e.printStackTrace();
        }
        clone.getBuilding().setLevel(this.getBuilding().getLevel());
        clone.getBuilding().setDome(this.getBuilding().getDome());
        return clone;
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

        /**
         * @return Returns a BlockLevel that indicates the level of the tower.
         */
        public BlockLevel getLevel() {
            return level;
        }

        /**
         * Set the new height of the tower. If you want you can calculate the next BlockLevel with nextLevel() in the BlockLevel enum.
         *
         * @param level The parameter is set as the new tower height.
         *///TODO va utilizzato solo per i test.
        void setLevel(BlockLevel level) {
            this.level = level;
        }

        /**
         * Questo va usato per costruire
         * TODO
         */
        public void addBlock() {
            if (this.level != BlockLevel.THREE) {
                this.level = this.level.nextLevel();
            } else {
                setDome(true);
            }
        }

        /**
         * Used to know if there is a dome on the tower.
         *
         * @return Returns a boolean true if there is false otherwise.
         */
        public Boolean getDome() {
            return dome;
        }

        /**
         * Used to set if the dome is present, true if is present false otherwise.
         *
         * @param dome Boolean true if you want set that there is a dome on the tile otherwise false
         */
        public void setDome(Boolean dome) {
            this.dome = dome;
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
        private IndexTile(int r, int c) {
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
    }
}