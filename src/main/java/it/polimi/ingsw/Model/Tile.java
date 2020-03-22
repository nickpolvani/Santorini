package it.polimi.ingsw.Model;


/**
 * The tile is the fundamental component of the IslandBoard.
 * It is these objects that keep the state of IslandBoard in memory.
 * @author Juri Sacchetta
 * @version 1.0
 */
public class Tile {

    /**
     * Default constructor
     */
    public Tile() {
        worker = null;
        blockLevel = BLOCKLEVEL.GROUND;
        dome = false;
    }

    /**
     * Worker instance present on the tile
     */
    private Worker worker;

    /**
     * Enum used to define the height of the tower
     */
    private BLOCKLEVEL blockLevel;

    /**
     * Boolean used to check if is present a dome
     */
    private Boolean dome;


    public Worker getWorker() {
        return worker;
    }

    /**
     * @param w worker instance to be placed
     */
    public void setWorker(Worker w) {
        this.worker = w;
    }

    public Boolean getDome() {
        return dome;
    }

    /**
     * @throws IllegalStateException When trying to build on a dome
     */
    public void setDome() throws IllegalStateException{
        if (dome)
            throw new IllegalStateException("building on a dome is not allowed!!");
        else
            this.dome = true;
    }

    /**
     * @return The BlOCKLEVEL indicating the height of the tower returns
     */
    public BLOCKLEVEL getHeight() {
        if(this.dome)
            return BLOCKLEVEL.DOME;
        else return this.blockLevel;
    }

    /**
     * @return true if is present a worker on the tile else false
     */
    public Boolean isOccupied() {
        return this.worker!=null;
    }

    /**
     * @return worker instance worker instance that was present on the tile
     */
    public Worker removeWorker() {
        Worker tmp=this.worker;
        setWorker(null);
        return tmp;
    }

    /**
     * Increases the level by that in memory.
     * Throw an exception if you try to build on a dome.
     * @throws IllegalStateException When trying to build on a dome
     */
    public void addBlock() throws IllegalStateException{
        switch (blockLevel) {
            case GROUND:
                blockLevel = BLOCKLEVEL.ONE;
                break;
            case ONE:
                blockLevel = BLOCKLEVEL.TWO;
                break;
            case TWO:
                blockLevel = BLOCKLEVEL.TREE;
                break;
            case TREE:
                setDome();
                break;
        }
    }
}