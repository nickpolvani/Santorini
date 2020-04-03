package it.polimi.ingsw.model.god;

import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.AthenaTurn;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Athena extends God {

    /**
     * Default constructor
     */
    protected Athena() {
        super(GodNameAndDescription.ATHENA);
        try {
            getGameState().setTurn(new AthenaTurn(getGameState()));
        } catch (AlreadySetException e) {
            System.out.println("Prendilo al culo (__o__) (o) (o)"); //TODO togli sta roba
        }

    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        // TODO implement here
        return null;
    }

}