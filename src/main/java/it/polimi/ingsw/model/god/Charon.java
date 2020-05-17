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

    private Tile.IndexTile opponentWorker;

    protected Charon(GameState gameState, Player player) {
        super(GodDescription.CHARON, player, gameState);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    public Queue<Operation> getRemainingOperations() {
        Operation[] operationsArray;
        Collection<Tile.IndexTile> neighbouringTiles = board.indexOfNeighbouringTiles(worker.getIndexTile());
        if (confirmed) {
            if (neighbouringTiles.stream().noneMatch(t -> board.getTile(t).getCurrentWorker() != null &&
                    board.getTile(t).getCurrentWorker().getColor() != worker.getColor())) {
                this.choiceNotAllowedMessage = "There's no neighbouring opponent Worker! Go on to the next Operation!";
                operationsArray = new Operation[]{Operation.MESSAGE_NO_REPLY, Operation.MOVE, Operation.BUILD};
            } else {
                operationsArray = new Operation[]{Operation.SELECT_OPPONENTS_WORKER, Operation.MESSAGE_NO_REPLY,
                        Operation.MOVE, Operation.BUILD};
            }
        } else {
            operationsArray = new Operation[]{Operation.MOVE, Operation.BUILD};
        }
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    public List<Tile.IndexTile> selectOpponentsWorker() {
        Collection<Tile.IndexTile> neighbouringTiles = board.indexOfNeighbouringTiles(worker.getIndexTile());
        return neighbouringTiles.stream()
                .filter(t -> board.getTile(t).getCurrentWorker() != null &&
                        board.getTile(t).getCurrentWorker().getColor() != worker.getColor())
                .collect(Collectors.toList());
    }

    public void moveWorker(Tile.IndexTile opponentWorker) {
        this.opponentWorker = opponentWorker;
        Tile.IndexTile oppositeTile = checkOppositeTile();
        if (oppositeTile == null) this.choiceNotAllowedMessage = "Sorry but you can't move the worker because the " +
                "space directly on the other side of your worker is already occupied.\nGo on to the next Operation!";
        else {
            try {
                board.changePosition(board.getCurrentWorker(opponentWorker), oppositeTile);
                this.choiceNotAllowedMessage = "Done! Go on to the next Operation!";
            } catch (AlreadyOccupiedException e) {
                e.printStackTrace();
            }
        }
    }

    private Tile.IndexTile checkOppositeTile() {
        int oppositeRow, oppositeCol;
        oppositeRow = (2 * worker.getIndexTile().getRow()) - opponentWorker.getRow();
        oppositeCol = (2 * worker.getIndexTile().getCol()) - opponentWorker.getCol();

        if (oppositeRow > 4 || oppositeRow < 0 || oppositeCol > 4 || oppositeCol < 0) return null;
        else
            return (!board.getTile(oppositeRow, oppositeCol).isOccupied() ? new Tile.IndexTile(oppositeRow, oppositeCol) : null);
    }

    @Override
    public void resetGodState() {
        super.resetGodState();
        opponentWorker = null;
    }
}
