package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;

import java.util.Queue;


/**
 * Type of Turn to be used after setup is finished
 */

public class BasicTurn extends Observable<Options> implements Turn {
    /**
     * Reference to the handler of the whole game: this is necessary to switch turn
     */
    private final GameState gameState;
    /**
     *
     */
    private Player currentPlayer;
    /**
     *
     */
    private Queue<Operation> turnOperations;

    /**
     * Default constructor that set only which game the turn belongs to.
     */
    public BasicTurn(GameState game, Player firstPlayer) {
        this.gameState = game;
        this.currentPlayer = firstPlayer;
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * Initializer of a new turn, personalized for the current player's god
     */
    public void switchTurn() {
        this.currentPlayer = gameState.getNextPlayer(this.currentPlayer);
        if (this.currentPlayer.getGod().checkHasLost()) this.gameState.setLooser(this.currentPlayer);
        else this.turnOperations = currentPlayer.getGod().getTurnOperations();
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
        if (turnOperations.isEmpty()) {
            switchTurn();
        }
        try {
            notify(currentPlayer.getGod().getOptions());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}