package it.polimi.ingsw.model.god;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Minotaur extends God {

    /**
     * Default constructor
     */
    protected Minotaur() {
        super(GodNameAndDescription.MINOTAUR);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        return super.tileToMove(indexTile);
    }

    @Override
    public void move(IndexTile indexTile) throws AlreadyOccupiedException {
        super.move(indexTile);
    }

    private void force(IndexTile indexTile) {
        // TODO implement here
    }
}