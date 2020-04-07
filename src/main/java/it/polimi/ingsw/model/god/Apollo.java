package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Apollo extends God {


    public Apollo(GameState gameState, Player player) {
        super(GodNameAndDescription.APOLLO, player, gameState);
    }

    /**
     * Default constructor
     */


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