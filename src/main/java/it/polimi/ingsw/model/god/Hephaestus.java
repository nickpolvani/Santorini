package it.polimi.ingsw.model.god;


import it.polimi.ingsw.model.Tile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Hephaestus extends God {

    /**
     * Default constructor
     */
    protected Hephaestus() {
        super(GodNameAndDescription.HEPHAESTUS);
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