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
public class Artemis extends God {

    public Artemis(GameState gameState, Player player) {
        super(GodNameAndDescription.ARTEMIS, player, gameState);
    }

    /**
     * Default constructor
     */


    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        // TODO implement here
        return null;
    }


    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }


}