package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Polvani-Puoti-Sacchetta
 * Before your Worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if that space is unoccupied.
 */
public class Charon extends God {

    protected Charon(GameState gameState, Player player) {
        super(GodDescription.CHARON, player, gameState);
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
            operationsArray = new Operation[]{Operation.SELECT_OPPONENTS_WORKER, Operation.MOVE, Operation.BUILD};
        } else {
            operationsArray = new Operation[]{Operation.MOVE, Operation.BUILD};
        }
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public boolean isChooseAvailable() {
        return !opponentsWorkerTile().isEmpty();
    }

    public List<Tile.IndexTile> opponentsWorkerTile() {
        Collection<Tile.IndexTile> neighbouringTiles = board.indexOfNeighbouringTiles(currentWorker.getIndexTile());
        return neighbouringTiles.stream()
                .filter(t -> board.getTile(t).getCurrentWorker() != null &&
                        board.getTile(t).getCurrentWorker().getColor() != currentWorker.getColor() &&
                        findOppositeTile(t) != null)
                .collect(Collectors.toList());
    }

    public void moveWorker(Tile.IndexTile opponentWorker) {
        Tile.IndexTile oppositeTile = findOppositeTile(opponentWorker);
        try {
            board.changePosition(board.getCurrentWorker(opponentWorker), oppositeTile);
        } catch (AlreadyOccupiedException e) {
            e.printStackTrace();
        }
    }

    private Tile.IndexTile findOppositeTile(Tile.IndexTile opponentWorker) {
        int oppositeRow;
        int oppositeCol;
        oppositeRow = (2 * currentWorker.getIndexTile().getRow()) - opponentWorker.getRow();
        oppositeCol = (2 * currentWorker.getIndexTile().getCol()) - opponentWorker.getCol();

        if (oppositeRow > 4 || oppositeRow < 0 || oppositeCol > 4 || oppositeCol < 0) return null;
        else
            return (!board.getTile(oppositeRow, oppositeCol).isOccupied() ? new Tile.IndexTile(oppositeRow, oppositeCol) : null);
    }
}