package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Apollo extends God {


    /**
     * Default constructor
     */
    Apollo() {
        super(GodNameAndDescription.APOLLO);

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
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));

    }

    private void force(IndexTile indexTile) {
        // TODO implement here
    }


}