package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.controller.turn.Turn;
import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.GodsFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class GameState {
    /**
     * The board where the game is about to be played
     */
    private final IslandBoard islandBoard;

    /**
     * necessary if you want to use endGame method of controller
     */
    private GameController gameController;
    /**
     *
     */
    private final List<Player> players;
    /**
     *
     */
    private final GodsFactory godsFactory;
    /**
     *
     */
    private Player challenger;
    /**
     *
     */
    private Turn turn;

    /**
     * Default constructor: the most important thing is that island board can be instanced one time per game. Therefore,
     * there's no setter for islandBoard.
     */
    public GameState(Set<String> nicknames) {
        if (nicknames.size() < 2 || nicknames.size() > 3) throw new IllegalArgumentException();
        this.islandBoard = new IslandBoard();
        this.players = new ArrayList<>();
        this.godsFactory = new GodsFactory(this);
        for (String n : nicknames) {
            players.add(new Player(n, this));
        }
        //TODO inizializzare il Turno, siamo in fase di setup dunque dovrebbe essere SetupTurn
    }

    public void setGameController(GameController gameController) throws AlreadySetException {
        if (this.gameController != null)
            throw new AlreadySetException("GameState and GameController are already bound");
        this.gameController = gameController;
    }

    /**
     * @return islandBoard: the real instance, not a clone of it.
     */
    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    /**
     * @return challenger of the match
     */
    public Player getChallenger() {
        return this.challenger;
    }

    /**
     * @param player chosen randomly for being the game's challenger
     */
    public void setChallenger(Player player) {
        this.challenger = player;
    }

    /**
     * getter of ArrayList players is useless because we have methods like  nextPlayer and getCurrentPlayer in BasicTurn object
     * but we use it for testing.
     */
    public List<Player> getPlayers() {
        return players;
    }

    public Turn getTurn() {
        return this.turn;
    }


    public void setTurn(Turn turn) throws IllegalArgumentException {
        if (this.turn != null) {
            if (this.turn instanceof BasicTurn && turn instanceof SetupTurn) {
                throw new IllegalArgumentException("cannot switch from game to setup turn");
            }
        }
        this.turn = turn;
    }

    public GodsFactory getGodsFactory() {
        return godsFactory;
    }

    /**
     * @return the player who has to play his gameTurn
     */
    public Player getNextPlayer(Player currentPlayer) {
        int numOfCurrentPlayer = this.players.indexOf(currentPlayer);
        try {
            return this.players.get(numOfCurrentPlayer + 1);
        } catch (IndexOutOfBoundsException e) {
            return this.players.get(0);
        }
    }

    public void setWinner(Player winner) {
        this.gameController.endGame(winner);
    }

    public void setLooser(Player looser) {
        this.gameController.hasLost(looser);
    }
}