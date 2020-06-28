package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.
 */
public class Athena extends God {

    /**
     * This boolean let us know if Athena, moving up to one level, prevents the opponents' worker from moving up.
     */
    private boolean canMoveUp;


    protected Athena(GameState gameState, Player player) {
        super(GodDescription.ATHENA, player, gameState);
        canMoveUp = true;
    }


    @Override
    public void move(Tile.IndexTile indexTile) throws AlreadyOccupiedException {
        if (!tileToMove(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        int levelDifference = (board.getBuildingLevel(indexTile) - board.getBuildingLevel(currentWorker.getIndexTile()));

        board.changePosition(currentWorker, indexTile);
        if (levelDifference > 0) {
            canMoveUp = false;
        }
        handleWinningCondition();
    }

    public boolean getCanMoveUp() {
        return canMoveUp;
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public void resetGodState() {
        canMoveUp = true;
    }
}