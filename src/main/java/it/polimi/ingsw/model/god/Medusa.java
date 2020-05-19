package it.polimi.ingsw.model.god;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.Queue;

/**
 * If possible, your Workers build in lower neighboring spaces that are
 * "occupied by opponent Workers, removing the opponent Workers from the game.
 */
public class Medusa extends God {
    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected Medusa(GameState gameState, Player player) {
        super(GodDescription.MEDUSA, player, gameState);
    }

    @Override
    public Queue<Operation> getTurnOperations() {
        return null;
    }
}
