package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.turn.BasicTurn;
import it.polimi.ingsw.controller.turn.SetupTurn;
import it.polimi.ingsw.controller.turn.Turn;
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
}