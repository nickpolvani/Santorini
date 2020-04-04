package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.AthenaGameTurn;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Operation;
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
    protected Athena(GameState gameState) {
        super(GodNameAndDescription.ATHENA, gameState);
        try {
            getGameState().setTurn(new AthenaGameTurn(getGameState(), gameState.getTurn().getCurrentPlayer()));
        } catch (IllegalArgumentException e) {
            System.err.println("Prendilo al culo (__o__) (o) (o)"); //TODO togli sta roba
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