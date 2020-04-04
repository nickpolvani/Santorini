package it.polimi.ingsw.controller;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
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
        try {
            gameState.setGameController(this);
        } catch (AlreadySetException e) {
            System.err.println(e.getMessage());
        }
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

    public void endGame(Player winner) {
        // TODO implement here
    }

    public void hasLost(Player looser) {
        // TODO implement here
    }

    public void selectChallenger() {
        // TODO implement here
    }

    public void selectGods() {
        // TODO implement here
    }


    synchronized
    @Override
    public void update(Action a) {
        /*TODO se non è il giocatore corrente viene scartata, qua non so se c'è bisogno del synchronized anche se credo di no*/
        if (a.getPlayer().equals(gameState.getTurn().getCurrentPlayer())) {
            try {
                actionHandler.start(a);
            } catch (AlreadyOccupiedException e) {
                e.printStackTrace();
            }
        }/* else { TODO ipotesi di risposta
            a.getPlayer().getView().send(new AnotherTurnException);
        }*/
    }
}