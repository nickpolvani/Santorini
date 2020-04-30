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
 * @author Polvani-Puoti-Sacchetta
 * Your Move: Your Worker may  move into an opponent Worker’s space, if their Worker can be
 * forced one space straight backwards to an unoccupied space at any level.
 */
public class Minotaur extends God {

    /**
     * Default constructor
     */
    protected Minotaur(GameState gameState, Player player) {
        super(GodDescription.MINOTAUR, player, gameState);

    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {

        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        Worker otherWorker;

        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {

            if (!(board.getTile(otherTile).getBuilding().getDome()) &&
                    board.getBuildingLevel(otherTile) - board.getBuildingLevel(positionTile.getIndex()) < 2) {

                otherWorker = board.getTile(otherTile).getCurrentWorker();
                if (otherWorker != null) {
                    if (checkBackwardsTile(indexTile, otherTile) && !Arrays.asList(player.getWorkers()).contains(otherWorker)) {
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

        if (!tileToMove(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to move worker is not allowed");
        }

        Worker opponentWorker = board.getTile(indexTile).getCurrentWorker();

        if (opponentWorker != null) {
            IndexTile whereForce = new IndexTile((2 * opponentWorker.getIndexTile().getRow() - worker.getIndexTile().getRow()),
                    (2 * opponentWorker.getIndexTile().getCol() - worker.getIndexTile().getCol()));

            board.changePosition(opponentWorker, whereForce);

        }
        board.changePosition(worker, indexTile);

        handleWinningCondition();
    }

    /**
     * @param myWorker       we need to pass as a parameter also the tile where i have the worker.
     *                       In fact, during the Option.SELECT_WORKER is checked if the current player
     *                       may not move any of his workers and the god does not have yet worker assigned (so NullPointerException).
     * @param opponentWorker the tile where there's the opponent team's worker which could be forced to the tile straight backwards
     * @return true if opponent worker can be forced; false otherwise.
     */
    protected boolean checkBackwardsTile(IndexTile myWorker, IndexTile opponentWorker) {
        IndexTile backwardsTile = new IndexTile((2 * opponentWorker.getRow() - myWorker.getRow()),
                (2 * opponentWorker.getCol() - myWorker.getCol()));

        if ((backwardsTile.getRow() > 4 || backwardsTile.getRow() < 0) || (backwardsTile.getCol() > 4 || backwardsTile.getCol() < 0))
            return false;
        else return !board.isOccupied(backwardsTile);
    }


}