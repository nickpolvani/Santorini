package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile;

import java.util.Collection;
import java.util.Queue;

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
    protected Collection<Tile.IndexTile> tileToMove(Tile.IndexTile indexTile) {
        return super.tileToMove(indexTile);
    }

    @Override
    public void move(Tile.IndexTile indexTile) {
        super.move(indexTile);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }
}