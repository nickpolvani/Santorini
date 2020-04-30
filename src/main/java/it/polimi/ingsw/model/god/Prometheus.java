package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 * Your Turn: If your Worker does not move up, it may build both before and after moving.
 */
public class Prometheus extends God {

    /**
     * Default constructor
     */
    protected Prometheus(GameState gameState, Player player) {
        super(GodDescription.PROMETHEUS, player, gameState);
    }

    /**
     * @param indexTile is the current position for the worker
     * @return the tile where the worker can move, according to the choice of the player
     * about using Prometheus' power
     */
    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        int maxLevelDifference = 2;

        if (confirmed = true) maxLevelDifference = 1;
        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!(board.isOccupied(otherTile)) &&
                    board.getBuildingLevel(otherTile) - positionTile.getBuildingLevel() < maxLevelDifference) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        Operation[] operationsArray;
        if (confirmed) {
            operationsArray = new Operation[]{Operation.BUILD, Operation.MOVE, Operation.BUILD};
        } else {
            operationsArray = new Operation[]{Operation.MOVE, Operation.BUILD};
        }

        return new LinkedList<>(Arrays.asList(operationsArray));
    }

}