package it.polimi.ingsw.Controller;

import it.polimi.ingsw.Bean.Choice.ConfirmChoice;
import it.polimi.ingsw.Bean.Choice.GodChoice;
import it.polimi.ingsw.Bean.Choice.TileChoice;
import it.polimi.ingsw.Controller.God.GodsFactory;
import it.polimi.ingsw.Model.GameState;
import it.polimi.ingsw.Observer.ObserverPlayerChoice;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class GameController implements ObserverPlayerChoice {

    private GodsFactory godsFactory;

    private GameState gameState;

    /**
     * Default constructor
     */
    public GameController() {
        gameState = new GameState();
        godsFactory = new GodsFactory();
        gameState.setGodsFactory(godsFactory);
    }

    public GodsFactory getGodsFactory() {
        return godsFactory;
    }

    /**
     *
     */
    public void init() {
        // TODO implement here
    }

    /**
     *
     */
    public void playGame() {
        // TODO implement here
    }

    /**
     *
     */
    public void playTurn() {
        // TODO implement here
    }

    /**
     *
     */
    public void endGame() {
        // TODO implement here
    }

    /**
     *
     */
    public void selectChallenger() {
        // TODO implement here
    }

    /**
     *
     */
    public void selectGods() {
        // TODO implement here
    }

    /**
     *
     */
    public void hasWon() {
        // TODO implement here
    }

    /**
     *
     */
    public void hasLost() {
        // TODO implement here
    }

    @Override
    public void update(TileChoice t) {

    }

    @Override
    public void update(ConfirmChoice c) {

    }

    @Override
    public void update(GodChoice g) {

    }
}