package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Atlas extends God {

    /**
     * Default constructor
     */
    protected Atlas(GameState gameState, Player player) {
        super(GodNameAndDescription.ATLAS, player, gameState);
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }



    @Override
    protected Collection<IndexTile> tileToBuild(IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(IndexTile indexTile) throws DomeAlreadyPresentException {
        super.build(indexTile);
    }

}