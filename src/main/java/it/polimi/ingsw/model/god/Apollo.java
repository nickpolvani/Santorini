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
 * APOLLO: Your Worker may  move into an opponent Worker’s space by forcing their Worker to
 * the space just vacated.
 */
public class Apollo extends God {


    public Apollo(GameState gameState, Player player) {
        super(GodDescription.APOLLO, player, gameState);
    }


    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        /*
        We overridden this method because Apollo allows players to move his worker also
        in a tile where another worker is in, switching positions of workers.
         */
        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        Worker otherWorker;

        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!(board.getDome(otherTile)) &&
                    board.getBuildingLevel(otherTile) - positionTile.getBuildingLevel() < 2) {
                otherWorker = board.getCurrentWorker(otherTile);
                if (otherWorker != null) {
                    if (!player.getWorkers().contains(otherWorker)) {
                        tileToMove.add(otherTile);
                    }
                } else {
                    tileToMove.add(otherTile);
                }

            }
        }
        return tileToMove;
    }


    @Override
    public void move(IndexTile indexTile) throws AlreadyOccupiedException {

        if (!tileToMove(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }
        Worker otherWorker = board.getCurrentWorker(indexTile);
        if (otherWorker != null) {
            IndexTile whereSwitch = currentWorker.getIndexTile();
            //updating currentWorker's State
            board.getTile(indexTile).setCurrentWorker(null);
            board.changePosition(currentWorker, indexTile);
            //updating otherWorker's State
            board.getTile(whereSwitch).setCurrentWorker(otherWorker);
            otherWorker.setIndexTile(whereSwitch);
        } else {
            board.changePosition(currentWorker, indexTile);
        }
        handleWinningCondition();
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

}