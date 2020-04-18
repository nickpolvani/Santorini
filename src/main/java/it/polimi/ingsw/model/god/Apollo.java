package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;
import it.polimi.ingsw.model.Worker;

import java.util.*;

/**
 * @author Puoti
 * APOLLO: Your Worker may  move into an opponent Worker’s space by forcing their Worker to
 * the space just vacated.
 */
public class Apollo extends God {

    /**
     * Default constructor
     */
    public Apollo(GameState gameState, Player player) {
        super(GodNameAndDescription.APOLLO, player, gameState);
    }


    /**
     * @param indexTile is the current position for the worker
     * @return tiles where player can move his worker. We overridden this method because Apollo allows players to move his worker also
     * in a tile where another worker is in, switching positions of workers.
     */
    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = gameState.getIslandBoard().getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();

        for (IndexTile otherTile : gameState.getIslandBoard().indexOfNeighbouringTiles(indexTile)) {
            if (!(gameState.getIslandBoard().getTile(otherTile).getBuilding().getDome()) &&
                    gameState.getIslandBoard().getTile(otherTile).getBuildingLevel() - positionTile.getBuildingLevel() < 2) {
                tileToMove.add(otherTile);
            }
        }
        return tileToMove;
    }


    @Override
    public void move(IndexTile indexTile) throws AlreadyOccupiedException {

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        Worker otherWorker = gameState.getIslandBoard().getTile(indexTile).getCurrentWorker();
        if (otherWorker != null) {
            IndexTile whereSwitch = worker.getIndexTile();
            //updating currentWorker's State
            gameState.getIslandBoard().getTile(indexTile).setCurrentWorker(null);
            gameState.getIslandBoard().changePosition(worker, indexTile);
            //updating otherWorker's State
            gameState.getIslandBoard().getTile(whereSwitch).setCurrentWorker(otherWorker);
            otherWorker.setIndexTile(whereSwitch);
        } else {
            gameState.getIslandBoard().changePosition(worker, indexTile);
        }

        handleWinningCondition();
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

}