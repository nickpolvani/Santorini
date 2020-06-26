package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Worker class represent the checker of the game. A Worker can only be on the game's board, in any tile.
 * A Worker always has a owner. The owner can have a maximum of 2 workers.
 * The game start with 2 workers for everyone player, but during the game it's possible for a player to lose one of his workers.
 * If a player loses both his workers lose.
 * Each worker can move one cell and build one block for turn.
 *
 * @see Player
 * @see it.polimi.ingsw.model.Tile.IndexTile
 * @see Color
 */
public class Worker implements Cloneable, Serializable {

    /**
     * The color of worker's team, which is chosen by the GameState constructor,
     * at the game's start, and it will be the same for the whole game.
     *
     * @see Color
     */
    private final Color color;

    /**
     * Every worker has a position on the island board, more specifically on a tile.
     * CurrentIndexTile represents the current position of the worker.
     * It cannot be null
     *
     * @see it.polimi.ingsw.model.Tile.IndexTile
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
     * @return The worker's color
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
    //TODO forse fa inserito il check che positionTile non può essere null, però da fastidio medusa che lo setta a null nel caso si cancelli un worker.
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
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return color == worker.color &&
                indexTile.equals(worker.indexTile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, indexTile);
    }
}