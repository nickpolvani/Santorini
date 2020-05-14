package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 * Your Worker may move one additional time, but not back to its initial space.
 */
public class Artemis extends God {

    private IndexTile tileFrom;


    /**
     * Default constructor
     */
    public Artemis(GameState gameState, Player player) {
        super(GodDescription.ARTEMIS, player, gameState);
        choiceNotAllowedMessage = "You cannot make this choice, but you will not loose the game because the operation is optional.";
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
    public void move(IndexTile indexTile) throws IllegalArgumentException, AlreadyOccupiedException {

        if (tileFrom == null) {
            tileFrom = worker.getIndexTile();
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
            if (tileToMove(worker.getIndexTile()).size() == 0) {
                return new LinkedList<>(Arrays.asList(Operation.MESSAGE_NO_REPLY, Operation.BUILD));
            } else {
                return new LinkedList<>(Arrays.asList(Operation.MOVE, Operation.BUILD));
            }
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
}