package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Artemis extends God {

    /**
     * Default constructor
     */
    protected Artemis() {
        super(GodNameAndDescription.ARTEMIS);
    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        // TODO implement here
        return null;
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }


}