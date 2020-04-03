package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.Operation;

import java.util.Queue;


/**
 * @author Francesco Puoti
 */
//TODO va rivista la comunicazione model->View in questa classe
public class Turn {
    /**
     * Reference to the handler of the whole game: this is necessary to switch turn
     */
    private final GameState gameState;

    private Player currentPlayer;


    private Queue<Operation> turnOperations;

    /**
     * Default constructor that set only which game the turn belongs to.
     * To set a new turn use switchTurn!
     */
    public Turn(GameState game) {
        this.gameState = game;
    }

    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Initializer of a new turn, personalized for the current player's god
     */
    public void switchTurn() {
        this.currentPlayer = gameState.getNextPlayer(this.currentPlayer);
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Operation getCurrentOperation() {
        return turnOperations.peek();
    }

    /**
     * called by God at the end of methods select_worker, move, build, applyChoice
     */
    public void endCurrentOperation() {
        turnOperations.poll();
    }

}