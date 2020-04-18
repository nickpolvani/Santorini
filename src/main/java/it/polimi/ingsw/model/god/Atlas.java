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
 * @author Polvani-Puoti-Sacchetta
 * Your Build: Your Worker may build a dome at any level.
 */
public class Atlas extends God {

    /**
     * Default constructor
     */
    protected Atlas(GameState gameState, Player player) {

        super(GodNameAndDescription.ATLAS, player, gameState);
        choiceMessage = "Your next operation is a build: your Worker can build a dome at any level because of Atlas' power." +
                "\nDo you want to use the power? (Yes/No)";
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

        if (!tileToBuild(worker.getIndexTile()).contains(indexTile)) {
            throw new IllegalArgumentException("Tile where you want to build is not allowed!");
        }

        if (confirmed) {
            gameState.getIslandBoard().getTile(indexTile).getBuilding().buildDome();
        } else {
            gameState.getIslandBoard().getTile(indexTile).getBuilding().addBlock();
        }
    }

}