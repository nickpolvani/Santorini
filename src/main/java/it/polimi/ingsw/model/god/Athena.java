package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Athena extends God {

    /**
     * Default constructor
     */
    protected Athena() {
        super(GodNameAndDescription.ATHENA);
    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile t) {
        // TODO implement here
        return null;
    }

}