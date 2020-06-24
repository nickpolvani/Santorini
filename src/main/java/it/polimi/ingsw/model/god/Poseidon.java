package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.Worker;

import java.util.*;

/**
 * If your unmoved Worker is on the ground level, it may build up to three times.
 */
public class Poseidon extends God {

    /**
     * The worker not selected to perform the turn
     */
    Worker unmovedWorker = null;

    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Poseidon(GameState gameState, Player player) {
        super(GodDescription.POSEIDON, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    /**
     * @return return the Tile where the unmoved worker can build
     */
    public Collection<Tile.IndexTile> unmovedWorkerTileToBuild() {
        return tileToBuild(unmovedWorker.getIndexTile());
    }

    @Override
    public Queue<Operation> getRemainingOperations() {
        if (confirmed) {
            return new LinkedList<>(Collections.singletonList(Operation.POSEIDON_BUILD));
        } else {
            return new LinkedList<>(Collections.singletonList(Operation.BUILD));
        }
    }

    /**
     * The overload of the method build inherited from the super class.
     *
     * @param tileToBuild   tileToBuild: The tile where you want to build
     * @param levelsToBuild levelsToBuild: The number of Blocks you want to build on the selected tile
     * @throws DomeAlreadyPresentException
     */
    public void build(Tile.IndexTile tileToBuild, int levelsToBuild) throws DomeAlreadyPresentException {
        if (board.getBuildingLevel(tileToBuild) + levelsToBuild > 4) throw new IllegalArgumentException();
        for (int i = 0; i < levelsToBuild; i++) {
            board.getTile(tileToBuild).getBuilding().addBlock();
        }
    }

    @Override
    public boolean isChooseAvailable() {
        //we have to do this check because there is Medusa, thus the otherWorker may have been deleted from the game
        unmovedWorker = findNotCurrentWorker();
        if (unmovedWorker == null) return false;
        return tileToBuild(unmovedWorker.getIndexTile()).size() > 0;
    }

    @Override
    public void resetGodState() {
        confirmed = false;
        unmovedWorker = null;
    }
}
