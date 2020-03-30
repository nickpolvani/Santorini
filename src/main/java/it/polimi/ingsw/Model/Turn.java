package it.polimi.ingsw.Model;

/**
 * @author Francesco Puoti
 */
public class Turn {
    /**
     * Reference to the handler of the whole game: this is necessary to switch turn
     */
    private GameState myGame;
    /**
     * A flag useful when one of the players has Athens a god
     */
    private Boolean canMoveUp;
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
        this.myGame = game;
        switchTurn();
    }

    public GameState getMyGame() {
        return this.myGame;
    }

    /**
     * Initializer of a new turn
     */
    public void switchTurn() {
        setCurrentPlayer(myGame.getNextPlayer(this.currentPlayer));
        setCanMoveUp(Boolean.TRUE);
        setHasToMove(Boolean.TRUE);
        setHasToBuild(Boolean.TRUE);
    }

    /**
     * @return currentPlayer
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * @param player: set the player who has to play in this turn.
     *                This method is used also to switch the turn.
     */
    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public Boolean getCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(Boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public Boolean getHasToMove() {
        return hasToMove;
    }

    public void setHasToMove(Boolean hasToMove) {
        this.hasToMove = hasToMove;
    }

    public Boolean getHasToBuild() {
        return hasToBuild;
    }

    public void setHasToBuild(Boolean hasToBuild) {
        this.hasToBuild = hasToBuild;
    }
}