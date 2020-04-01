package it.polimi.ingsw.controller;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.observer.Observer;

/**
 * @author Polvani-Puoti-Sacchetta
 */


public class GameController implements Observer<Action> {

    private final GameState gameState;
    private final ActionHandler actionHandler = new ActionHandler();

    /**
     * Default constructor
     */
    public GameController(GameState gameState) {

        this.gameState = gameState;
    }

    public void init() {
        // TODO implement here

    }

    public void playGame() {
        // TODO implement here
    }

    public void playTurn() {
        // TODO implement here
    }

    public void endGame() {
        // TODO implement here
    }

    public void selectChallenger() {
        // TODO implement here
    }

    public void selectGods() {
        // TODO implement here
    }

    public void hasWon() {
        // TODO implement here
    }

    public void hasLost() {
        // TODO implement here
    }

    synchronized
    @Override
    public void update(Action a) {
        /*TODO se non è il giocatore corrente viene scartata, qua non so se c'è bisogno del synchronized anche se credo di no*/
        if (a.getPlayer().equals(gameState.getTurn().getCurrentPlayer())) {
            actionHandler.start(a);
        }/* else { TODO ipotesi di risposta
            a.getPlayer().getView().send(new AnotherTurnException);
        }*/
    }
}