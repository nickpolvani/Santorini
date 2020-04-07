package it.polimi.ingsw.model;

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
    }

    /**
     * @return islandBoard: the real instance, not a clone of it.
     */
    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    /**
     * getter of ArrayList players is useless because we have methods like  nextPlayer and getCurrentPlayer in BasicTurn object
     * but we use it for testing.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return
     */
    public GodsFactory getGodsFactory() {
        return godsFactory;
    }
}