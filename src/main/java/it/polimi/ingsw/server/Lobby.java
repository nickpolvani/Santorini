package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The lobby contains the reference to all the objects needed to play a game.
 * There are as many lobbies on the server as there are games in progress.
 */
public class Lobby {
    private static final Logger logger = Logger.getLogger("Server");
    /**
     * Indicates the number of participants, which can be either 2 or 3
     */
    public final int size;
    /**
     * The unique id of the lobby
     */
    public final int id;
    private final List<RemoteView> remoteViews = new ArrayList<>();
    private GameState gameState;
    private GameController gameController;
    private boolean started = false;
    private boolean full = false;
    private boolean close = false;

    public Lobby(int size, int id) {
        if (size != 2 && size != 3) throw new IllegalArgumentException();
        this.size = size;
        this.id = id;
    }

    boolean isFull() {
        return full;
    }

    /**
     * Method allows you to close the lobby and to interrupt the connections on the server-side with the clients
     */
    synchronized void close() {
        started = false;
        close = true;
        for (RemoteView w : remoteViews) {
            w.closeConnection();
        }
        logger.info("Closed lobby ID=" + id);
    }

    /**
     * Adds a player to the lobby
     *
     * @param username         The player's username
     * @param clientConnection The player's clientConnection
     */
    synchronized void addClient(RemoteView remoteView) {
        if (this.isStarted() || this.isFull() || this.isClose()) throw new IllegalStateException();
        if (remoteView.getUsername() == null || remoteView.getUsername().isEmpty() || containsUser(remoteView.getUsername()))
            throw new IllegalArgumentException();
        this.remoteViews.add(remoteView);
        logger.info("Registered username " + remoteView.getUsername() + " in the lobby ID=" + id);
        if (remoteViews.size() == size) {
            full = true;
            logger.debug("Lobby ID=" + id + " is full");
            this.start();
        }
    }

    /**
     * Check if a player is contained in the lobby
     *
     * @param nickname The player's username
     * @return Returns true if the player is contained in the lobby, otherwise false
     */
    boolean containsUser(String nickname) {
        return this.remoteViews.stream().anyMatch(w -> w.getUsername().equals(nickname));
    }

    /**
     * Create instances of remoteView, Controller and Model and launch the lobby game.
     */
    synchronized void start() {
        if (remoteViews.size() != size || isStarted()) {
            if (isStarted()) logger.error("Tried to initialize a started lobby");
            else logger.error("Tried to initialize a incomplete lobby");
            throw new IllegalStateException();
        }
        started = true;
        gameState = new GameState(remoteViews.stream().map(RemoteView::getUsername).collect(Collectors.toSet()));
        gameController = new GameController(gameState, this);
        for (RemoteView v : remoteViews) {
            gameController.addObserver(v);
            gameController.getTurn().addObserver(v);
            v.addObserver(gameController);
        }
        logger.info("Started lobby ID=" + id);
        gameController.start(); //Starts the game.
    }

    /**
     * @return Returns true if the lobby is started, otherwise false
     */
    public boolean isStarted() {
        return started;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameController getGameController() {
        return gameController;
    }

    /**
     * Allows you to remove a player in the lobby.
     * If the lobby is in the started state, throw a RunTimeException IllegalStateException
     *
     * @param username The player's username
     */
    public void removePlayer(String username) {
        if (isStarted()) {
            logger.fatal("Trying to remove a player from a started lobby without close lobby");
            throw new IllegalStateException();
        }
        remoteViews.removeIf(w -> w.getUsername().equals(username));
        logger.info("Removed " + username + " form not started lobby ID=" + id);
    }

    public boolean isClose() {
        return close;
    }

    int getActualSize() {
        return remoteViews.size();
    }

    Set<String> usernameSet() {
        return remoteViews.stream().map(RemoteView::getUsername).collect(Collectors.toSet());
    }
}
