package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Your Build: Your Worker may build a dome at any level.
 */
public class Atlas extends God {

    protected Atlas(GameState gameState, Player player) {

        super(GodDescription.ATLAS, player, gameState);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        return new LinkedList<>(Collections.singletonList(Operation.BUILD));
    }

    @Override
    public void build(IndexTile indexTile) throws DomeAlreadyPresentException {

        if (!tileToBuild(currentWorker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }
        if (confirmed) {
            board.getTile(indexTile).getBuilding().buildDome();
        } else {
            board.getTile(indexTile).getBuilding().addBlock();
        }
    }

}