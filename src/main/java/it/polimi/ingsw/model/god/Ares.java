package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * End of Your Turn: You may remove an unoccupied block (not dome) neighboring your unmoved Worker
 */
public class Ares extends God {

    /**
     * worker not selected for this turn
     */
    Worker unmovedWorker = null;

    protected Ares(GameState gameState, Player player) {
        super(GodDescription.ARES, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        Operation[] operations;
        if (confirmed) {
            operations = new Operation[]{Operation.REMOVE_BLOCK};
        } else {
            operations = new Operation[]{};
        }
        return new LinkedList<>(Arrays.asList(operations));
    }

    public void removeBlock(Tile.IndexTile indexTile) throws DomeAlreadyPresentException {
        if (!tileToRemoveBlock().contains(indexTile)) throw new IllegalArgumentException();
        board.getTile(indexTile).getBuilding().removeBlock();
    }

    @Override
    public boolean isChooseAvailable() {
        //we have to do this check because there is Medusa, thus the otherWorker may have been deleted from the game
        unmovedWorker = findNotCurrentWorker();
        if (unmovedWorker == null) return false;
        return tileToBuild(unmovedWorker.getIndexTile()).stream().anyMatch(t -> board.getBuildingLevel(t) != 0);
    }


    public Collection<Tile.IndexTile> tileToRemoveBlock() {

        return tileToBuild(unmovedWorker.getIndexTile()).stream()
                .filter(t -> board.getBuildingLevel(t) != 0).collect(Collectors.toList());
    }

    @Override
    public void resetGodState() {
        unmovedWorker = null;
        super.resetGodState();
    }
}
