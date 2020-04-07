package it.polimi.ingsw.controller;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionHandler;
import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.controller.turn.Turn;
import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;

/**
 * @author Polvani-Puoti-Sacchetta
 */

public class GameController implements Observer<Action> {

    private Turn turn;

    private final GameState gameState;

    private final ActionHandler actionHandler = new ActionHandler();

    /**
     * Default constructor
     */
    public GameController(GameState gameState) {
        this.gameState = gameState;

        //TODO implement random choice of challenger
        this.turn = new SetupTurn(gameState.getPlayers().get(0));
    }

    private Turn getTurn() {
        return turn;
    }

    /**
     * @return the player who has to play his gameTurn
     */
    public Player getNextPlayer(Player currentPlayer) {
        int numOfCurrentPlayer = gameState.getPlayers().indexOf(currentPlayer);
        try {
            return gameState.getPlayers().get(numOfCurrentPlayer + 1);
        } catch (IndexOutOfBoundsException e) {
            return gameState.getPlayers().get(0);
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

    public void hasWon(Player winner) {
        // TODO deve gestire la chiusura del gioco
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


    @Override
    public synchronized void update(Action a) {

        if (a.getPlayer().equals(getTurn().getCurrentPlayer())) {

            if (a.isCompatible(turn.getCurrentOperation())) {
                try {
                    actionHandler.start(a);
                    turn.endCurrentOperation();
                } catch (AlreadyOccupiedException | DomeAlreadyPresentException e) {
                    //TODO: it has to be implemented (mann l'error strunz! )
                }

            } else {
                //TODO send error's notification to the client
            }

        }/* else { TODO ipotesi di risposta
            a.getPlayer().getView().send(new AnotherTurnException);
        }*/
    }


}