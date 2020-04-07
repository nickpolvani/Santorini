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
 * @author Polvani-Puoti-Sacchetta
 */
public class Athena extends God {

    /**
     * Default constructor
     */
    protected Athena(GameState gameState, Player player) {
        super(GodNameAndDescription.ATHENA, player, gameState);
    }

    /**
     * @param indexTile is the tile chosen by the player to move the worker selected at the beginning of the turn.
     * @throws IllegalArgumentException
     * @throws IllegalStateException    thrown if current operation in turn is not MOVE
     */
    @Override
    public void move(Tile.IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException, IllegalStateException {
        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        int levelDifference = (gameState.getIslandBoard().getTile(indexTile).getBuildingLevel() - gameState.getIslandBoard().getTile(this.worker.getIndexTile()).getBuildingLevel());

        gameState.getIslandBoard().changePosition(worker, indexTile);
        if (levelDifference > 0) {
            //TODO come gestiamo il turno con athena anche per gli altri??
        }
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));

    }

}