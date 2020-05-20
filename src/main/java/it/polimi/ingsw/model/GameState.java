package it.polimi.ingsw.model;

import it.polimi.ingsw.model.god.GodsFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The GameState is the core of the model, from this class you can reach every class of the model.
 * There is a GameState's instance foreach different lobby. Every instances are independent by other one.
 *
 * @see IslandBoard
 * @see Player
 * @see GodsFactory
 */
public class GameState {
    /**
     * The IslandBoard's instance of the current lobby.
     *
     * @see IslandBoard
     */
    private final IslandBoard islandBoard;

    /**
     * The list of the current players in the lobby.
     * @see Player
     * @see List
     */
    private final List<Player> players;

    /**
     * The GodsFactory instance of the current lobby. It is used early in the game to create instances of the gods.
     * @see GodsFactory
     */
    private final GodsFactory godsFactory;


    /**
     * Default constructor
     */
    public GameState(Set<String> nicknames) {
        Logger logger = Logger.getLogger("Server");
        logger.debug("Start to initialize the model");
        if (nicknames.size() < 2 || nicknames.size() > 3) throw new IllegalArgumentException();
        this.islandBoard = new IslandBoard();
        this.players = new ArrayList<>();
        this.godsFactory = new GodsFactory(this);
        int i = 0;
        for (String n : nicknames) {
            players.add(new Player(n, this, Color.parseColor(i)));
            i++;
        }
        logger.debug("Model initialized!");
    }

    /**
     * @return islandBoard: the real instance, not a clone of it.
     */
    public IslandBoard getIslandBoard() {
        return islandBoard;
    }

    /**
     * getter of ArrayList players is little used because we have methods like nextPlayer() and getCurrentPlayer() in BasicTurn.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return Return the instance of GodsFactory
     */
    public GodsFactory getGodsFactory() {
        return godsFactory;
    }
}