package it.polimi.ingsw.model;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.observer.Observable;


/**
 * @author Francesco Puoti
 */
//TODO va rivista la comunicazione model->View in questa classe
public class Turn extends Observable<Options> {
    /**
     * Reference to the handler of the whole game: this is necessary to switch turn
     */
    private final GameState gameState;
    /**
     * A flag that check if player has still to move
     */
    private Boolean hasToMove;
    /**
     * A flag that check if player has still to Build: set True by the constructor of the class
     */
    private Boolean hasToBuild;
    /**
     * player who is playing his turn
     */
    private Player currentPlayer;

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
     * Initializer of a new turn
     */
    public void switchTurn() {
        setCurrentPlayer(gameState.getNextPlayer(this.currentPlayer));
        setHasToMove(Boolean.TRUE);
        setHasToBuild(Boolean.TRUE);
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * @param player: set the player who has to play in this turn. This method is used also to switch the turn.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public boolean getHasToMove() {
        return hasToMove;
    }

    public void setHasToMove(Boolean hasToMove) {
        this.hasToMove = hasToMove;
    }


    public boolean getHasToBuild() {
        return hasToBuild;
    }

    public void setHasToBuild(Boolean hasToBuild) {
        this.hasToBuild = hasToBuild;
    }


    public God getCurrentPlayerGod() throws IllegalAccessException {
        return gameState.getGodsFactory().getGod(currentPlayer.getGod().getNameAndDescription());
    }

    public boolean isTurnOver() throws IllegalAccessException {
        return getCurrentPlayerGod().isTurnOver();
    }


    //TODO Qua c'è da guardare
    /**
     * sends message to View containing updates of the GameState and the Options
     * available for the currentPlayer. Options can be an array of Tiles to choose from,
     * or a confirm choice (Yes/No)
     */
     /*
    public void askPlayerChoice(){
        God currentGod = getCurrentPlayerGod();
        if (currentGod.getCurrentChoiceType() == ChoiceType.CHOOSETILE){
            notify(currentGod.createTileOptions());
        }
        else if (currentGod.getCurrentChoiceType() == ChoiceType.CONFIRM){
            notify(currentGod.createConfirmOptions());
        }
    }*/

}