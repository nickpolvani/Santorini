package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

import java.util.List;
import java.util.Queue;


/**
 * Type of Turn to be used after setup is finished
 */

public class BasicTurn extends Observable<Options> implements Turn {
    /**
     *
     */
    private Player currentPlayer;
    /**
     *
     */
    private final GameController gameController;
    /**
     *
     */
    private Queue<Operation> turnOperations;

    /**
     * Default constructor that set only which game the turn belongs to.
     */
    public BasicTurn(GameController gameController, Player firstPlayer, List<Observer<Options>> observerList) {
        this.currentPlayer = firstPlayer;
        this.turnOperations = currentPlayer.getGod().getTurnOperations();
        this.gameController = gameController;
        this.observers.addAll(observerList);
        notify(currentPlayer.getGod().getOptions(getCurrentOperation()));
    }

    /**
     * Initializer of a new turn, personalized for the current player's god
     */
    public void switchTurn() {
        currentPlayer = gameController.getNextPlayer(currentPlayer);
        if (currentPlayer.getGod().cannotMove()) gameController.hasLost(currentPlayer);
        else {
            this.turnOperations = currentPlayer.getGod().getTurnOperations();
            notify(currentPlayer.getGod().getOptions(getCurrentOperation())); //this notify is used to notify the new current player about his first move
        }
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

        if (currentPlayer.isWinner()) gameController.hasWon(currentPlayer);
        else {
            turnOperations.poll();

            if (turnOperations.isEmpty()) {
                switchTurn();
            } else if (getCurrentOperation() == Operation.BUILD && currentPlayer.getGod().cannotBuild()) {
                gameController.hasLost(currentPlayer);
                switchTurn(); //this because after removing the looser whe have to notify the next player
            } else {
                try {
                    notify(currentPlayer.getGod().getOptions(getCurrentOperation()));
                } catch (IllegalStateException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        }

    }
}