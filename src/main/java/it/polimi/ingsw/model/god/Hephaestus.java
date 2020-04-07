package it.polimi.ingsw.model.god;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Hephaestus extends God {

    private Tile.IndexTile tileForAdditionalBlock;

    /**
     * Default constructor
     */
    protected Hephaestus(GameState gameState, Player player) {
        super(GodNameAndDescription.HEPHAESTUS, player, gameState);
    }

    @Override
    protected Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(Tile.IndexTile indexTile) throws DomeAlreadyPresentException {
        super.build(indexTile);
        tileForAdditionalBlock = indexTile;
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        Operation[] operationsArray = {Operation.SELECT_WORKER, Operation.MOVE, Operation.BUILD, Operation.CHOOSE};
        return new LinkedList<>(Arrays.asList(operationsArray));
    }

    @Override
    public void applyChoice(boolean confirm) throws RuntimeException, DomeAlreadyPresentException {
        if (confirm) {
            gameState.getIslandBoard().getTile(tileForAdditionalBlock).getBuilding().addBlock();
        }
        tileForAdditionalBlock = null;
    }
}