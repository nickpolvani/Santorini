package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Atlas extends God {

    private IndexTile probablyDome;
    private boolean confirmed;

    /**
     * Default constructor
     */
    protected Atlas(GameState gameState, Player player) {
        super(GodNameAndDescription.ATLAS, player, gameState);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE, Operation.BUILD};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    protected Collection<IndexTile> tileToBuild(IndexTile tile) {
        return super.tileToBuild(tile);
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

    @Override
    public void applyChoice(boolean confirm) throws RuntimeException {
        confirmed = confirm;
    }
}