package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Queue;

/**
 * "Your Worker may build a block under itself."
 */
public class Zeus extends God {
    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Zeus(GameState gameState, Player player) {
        super(GodDescription.ZEUS, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }
}
