package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.Queue;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Athena extends God {

    /**
     * Default constructor
     */
    protected Athena(GameState gameState, Player player) {
        super(GodNameAndDescription.ATHENA, player, gameState);
    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        // TODO implement here
        return null;
    }

}