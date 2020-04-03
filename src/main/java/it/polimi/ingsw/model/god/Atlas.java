package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Atlas extends God {

    /**
     * Default constructor
     */
    protected Atlas() {
        super(GodNameAndDescription.ATLAS);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }



    @Override
    protected Collection<IndexTile> tileToBuild(IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(IndexTile indexTile) {
        super.build(indexTile);
    }

}