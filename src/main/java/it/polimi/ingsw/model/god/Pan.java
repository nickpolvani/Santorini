package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Pan extends God {

    /**
     * Default constructor
     */
    protected Pan() {
        super(GodNameAndDescription.PAN);
    }

    @Override
    protected Collection<Tile.IndexTile> tileToMove(Tile.IndexTile tile) {
        return super.tileToMove(tile);
    }

    @Override
    public void move(Tile.IndexTile indexTile) {
        super.move(indexTile);
    }
}