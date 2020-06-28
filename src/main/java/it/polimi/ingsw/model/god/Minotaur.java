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
 * Your Move: Your Worker may  move into an opponent Worker’s space, if their Worker can be
 * forced one space straight backwards to an unoccupied space at any level.
 */
public class Minotaur extends God {


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
                    if (checkBackwardsTile(indexTile, otherTile) && !player.getWorkers().contains(otherWorker)) {
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

        Worker opponentWorker = board.getTile(indexTile).getCurrentWorker();

        if (opponentWorker != null) {
            IndexTile whereForce = new IndexTile((2 * opponentWorker.getIndexTile().getRow() - currentWorker.getIndexTile().getRow()),
                    (2 * opponentWorker.getIndexTile().getCol() - currentWorker.getIndexTile().getCol()));

            board.changePosition(opponentWorker, whereForce);

        }
        board.changePosition(currentWorker, indexTile);

        handleWinningCondition();
    }

    /**
     * This method is used to check if the selected worker can push the neighbouring opponent's worker to his backwards tile.
     *
     * @param myWorker       myWorker : The tile where I've my worker positioned
     * @param opponentWorker opponentWorker : The tile where there's the opponent team's worker which could
     *                       be forced to the tile straight backwards
     * @return true if opponent worker can be forced; false otherwise.
     */
    protected boolean checkBackwardsTile(IndexTile myWorker, IndexTile opponentWorker) {
        /*
            We need to pass as a parameter also the tile where there's the worker we want to check.
            In fact, during the Option.SELECT_WORKER is checked if the current player
            may not move any of his workers and the god does not have yet currentWorker assigned (so NullPointerException).
         */
        IndexTile backwardsTile = new IndexTile((2 * opponentWorker.getRow() - myWorker.getRow()),
                (2 * opponentWorker.getCol() - myWorker.getCol()));

        if ((backwardsTile.getRow() > 4 || backwardsTile.getRow() < 0) || (backwardsTile.getCol() > 4 || backwardsTile.getCol() < 0))
            return false;
        else return !board.isOccupied(backwardsTile);
    }


}