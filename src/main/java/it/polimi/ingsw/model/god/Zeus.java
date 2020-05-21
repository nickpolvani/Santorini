package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * "Your Worker may build a block under itself."
 */
public class Zeus extends God {
    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Zeus(GameState gameState, Player player) {
        super(GodDescription.ZEUS, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return new LinkedList<>(Arrays.asList(Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE));
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            return new LinkedList<>();
        } else {
            return new LinkedList<>(Collections.singletonList(Operation.BUILD));
        }
    }

    @Override
    public boolean isChooseAvailable() {
        return board.getBuildingLevel(currentWorker.getIndexTile()) < 3;
    }

    @Override
    public void applyChoice(boolean confirm) {
        if (confirm) {
            this.confirmed = true;
            if (board.getBuildingLevel(currentWorker.getIndexTile()) == 3) throw new IllegalStateException();
            try {
                board.getTile(currentWorker.getIndexTile()).setCurrentWorker(null);
                board.addBlock(currentWorker.getIndexTile());
                board.getTile(currentWorker.getIndexTile()).setCurrentWorker(currentWorker);
            } catch (AlreadyOccupiedException | DomeAlreadyPresentException e) {
                e.printStackTrace();
            }
        }
    }
}
