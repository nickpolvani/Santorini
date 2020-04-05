package it.polimi.ingsw.model;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Worker implements Cloneable {

    /**
     * The color of worker's team, which is chosen by the client, at the game's start, and it will be the same for the whole game.
     */
    private final Color color;

    /**
     * every worker has a position on the island board, more specifically on a tile.
     * positionTile represents the current position of the worker
     */
    private Tile.IndexTile positionTile;

    /**
     * Default constructor
     */
    public Worker(Tile.IndexTile positionTile, Color color) {
        this.positionTile = positionTile;
        this.color = color;
    }

    /**
     * @return this.color
     */
    public Color getColor() {
        return this.color;
    }


    /**
     * @return this.positionTile
     */
    public Tile.IndexTile getIndexTile() {
        return this.positionTile;
    }

    /**
     * @param positionTile :the reference of the new tile where a player decides to move his worker
     */
    public void setIndexTile(Tile.IndexTile positionTile) {
        this.positionTile = positionTile;
    }

    @Override
    protected Worker clone() throws CloneNotSupportedException {
        return (Worker) super.clone();
    }
}