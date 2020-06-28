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
 * PAN: YOU ALSO WIN IF YOUR WORKER MOVES DOWN TWO OR MORE LEVELS
 */
public class Pan extends God {

    protected Pan(GameState gameState, Player player) {
        super(GodDescription.PAN, player, gameState);
    }


    @Override
    public void move(Tile.IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException, IllegalStateException {
        if (!tileToMove(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        int levelDifference = (board.getBuildingLevel(currentWorker.getIndexTile()) - board.getBuildingLevel(indexTile));

        board.changePosition(currentWorker, indexTile);

        if (levelDifference > 1) player.setWinner(true);
        else handleWinningCondition();

    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }


}