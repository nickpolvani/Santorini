package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Queue;

/**
 * If your unmoved Worker is on the ground level, it may build up to three times.
 */
public class Poseidon extends God {
    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Poseidon(GameState gameState, Player player) {
        super(GodDescription.POSEIDON, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }
}
