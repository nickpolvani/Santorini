package it.polimi.ingsw.model;

import java.util.Objects;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Worker implements Cloneable {

    /**
     * The color of worker's team, which is chosen by the Client, at the game's start, and it will be the same for the whole game.
     */
    private final Color color;

    /**
     * every worker has a position on the island board, more specifically on a tile.
     * positionTile represents the current position of the worker
     */
    private Tile.IndexTile indexTile;

    /**
     * Default constructor
     */
    public Worker(Tile.IndexTile indexTile, Color color) {
        this.indexTile = indexTile;
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
        return this.indexTile;
    }

    /**
     * @param positionTile :the reference of the new tile where a player decides to move his worker
     */
    public void setIndexTile(Tile.IndexTile positionTile) {
        this.indexTile = positionTile;
    }

    @Override
    public Worker clone() {
        return new Worker(this.indexTile.clone(), this.color);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return color == worker.color &&
                Objects.equals(indexTile, worker.indexTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, indexTile);
    }
}