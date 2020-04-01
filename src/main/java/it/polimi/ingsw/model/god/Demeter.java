package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class  Demeter extends God {

    /**
     * Default constructor
     */
    protected Demeter() {
        super(GodNameAndDescription.DEMETER);
    }

    @Override
    protected Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(Tile.IndexTile indexTile) {
        super.build(indexTile);
    }
}