package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Your Worker may build one additional time, but this cannot be on a perimeter space.
 */
public class Hestia extends God {

    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Hestia(GameState gameState, Player player) {
        super(GodDescription.HESTIA, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }


    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            return new LinkedList<>(Arrays.asList(Operation.BUILD));
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public boolean isChooseAvailable() {
        int col = worker.getIndexTile().getCol();
        int row = worker.getIndexTile().getRow();
        return (col != 0 && col != 4 && row != 0 && row != 4 && tileToBuild(worker.getIndexTile()).size() != 0);
    }
}
