package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Demeter extends God {

    /**
     * Default constructor
     */
    protected Demeter(GameState gameState, Player player) {
        super(GodNameAndDescription.DEMETER, player, gameState);
    }

    @Override
    protected Collection<Tile.IndexTile> tileToBuild(Tile.IndexTile tile) {
        return super.tileToBuild(tile);
    }

    @Override
    public void build(Tile.IndexTile indexTile) throws DomeAlreadyPresentException {
        super.build(indexTile);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }
}