package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Worker implements Cloneable {

    /**
     * The color of worker's team, which is chosen by the client, at the game's start, and it will be the same for the whole game.
     */
    private Color color;

    /**
     * every worker has a position on the island board, more specifically on a tile.
     * positionTile represents the current position of the worker
     */
    private Tile.IndexTile positionTile;

    /**
     * Default constructor
     */
    public Worker(Tile.IndexTile positionTile) {
        this.positionTile = positionTile;
    }

    /**
     * @return this.color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * @param color is the color chosen by the player for his workers' team. I decided to let it trows exception if player tries
     *              to change it after the first set.
     */
    public void setColor(Color color) throws AlreadySetException {
        if (this.color != null) throw new AlreadySetException("Color alreay set!");
        this.color = color;
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
    protected Worker clone() {
        Worker clone = new Worker(this.positionTile);
        clone.color = this.color;
        return clone;
    }
}