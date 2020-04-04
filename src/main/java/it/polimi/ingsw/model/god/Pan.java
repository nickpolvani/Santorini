package it.polimi.ingsw.model.god;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti
 * <p>
 * PAN: YOU ALLSO WIN IF YOUR WORKER MOVES DOWN TWO OR MORE LEVELS
 */
public class Pan extends God {

    /**
     * Default constructor
     */
    protected Pan(GameState gameState) {
        super(GodNameAndDescription.PAN, gameState);
    }


    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning
     *                  of the turn.
     * @throws IllegalArgumentException
     */
    @Override
    public void move(Tile.IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {
        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        int levelDifference = (gameState.getIslandBoard().getTile(this.worker.getIndexTile()).getBuildingLevel() - gameState.getIslandBoard().getTile(indexTile).getBuildingLevel());

        gameState.getIslandBoard().changePosition(worker, indexTile);
        if (levelDifference > 1) this.gameState.setWinner(this.gameState.getTurn().getCurrentPlayer());
        gameState.getTurn().endCurrentOperation();
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));

    }


}