package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.*;

/**
 * Your Worker may move one additional time, but not back to its initial space.
 */
public class Artemis extends God {

    private IndexTile tileFrom;

    public Artemis(GameState gameState, Player player) {
        super(GodDescription.ARTEMIS, player, gameState);
    }


    @Override
    public Collection<IndexTile> tileToMove(IndexTile indexTile) {
        Tile positionTile = board.getTile(indexTile);
        Collection<IndexTile> tileToMove = new ArrayList<>();
        for (IndexTile otherTile : board.indexOfNeighbouringTiles(indexTile)) {
            if (!(board.isOccupied(otherTile)) &&
                    board.getBuildingLevel(otherTile) - positionTile.getBuildingLevel() < 2) {
                if (tileFrom != null) {
                    if (!otherTile.equals(tileFrom)) {
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

        if (tileFrom == null) {
            tileFrom = currentWorker.getIndexTile();
        }
        super.move(indexTile);

    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            return new LinkedList<>(Arrays.asList(Operation.MOVE, Operation.BUILD));
        } else {
            return new LinkedList<>(Collections.singletonList(Operation.BUILD));
        }
    }

    public IndexTile getTileFrom() {
        return tileFrom;
    }

    @Override
    public void resetGodState() {
        tileFrom = null;
        confirmed = false;
    }

    @Override
    public boolean isChooseAvailable() {
        return !tileToMove(currentWorker.getIndexTile()).isEmpty();
    }
}