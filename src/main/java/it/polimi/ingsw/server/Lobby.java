package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    public final int size;
    private final List<RemoteView> remoteViews = new ArrayList<>();
    private final Map<String, ClientConnection> connectionMap = new LinkedHashMap<>();
    public final int id;
    private final Logger logger = Logger.getLogger("Server");
    private GameState gameState;
    private GameController gameController;
    //TODO sistemare la storia dei flag
    private boolean started = false;
    private boolean full = false;

    public Lobby(int size, int id) {
        if (size != 2 && size != 3) throw new IllegalArgumentException();
        this.size = size;
        this.id = id;
    }

    Map<String, ClientConnection> getConnectionMap() {
        return connectionMap;
    }

    boolean isFull() {
        return full;
    }

    public void close() {
        started = false;
        for (RemoteView w : remoteViews) {
            w.getClientConnection().closeConnection();
        }
        logger.info("Closed lobby ID=" + id);
    }

    synchronized void addClient(String username, ClientConnection clientConnection) {
        if (this.isStarted() || this.isFull()) throw new IllegalStateException();
        if (this.connectionMap.containsKey(username)) throw new IllegalArgumentException();
        this.connectionMap.put(username, clientConnection);
        logger.info("Registered username " + username + " in the lobby ID=" + id);
        if (connectionMap.size() == size) {
            full = true;
            logger.debug("Lobby ID=" + id + " is full");
            this.start();
        }
    }

    boolean containsUser(String nickname) {
        return this.getConnectionMap().containsKey(nickname);
    }

    synchronized void start() {
        if (isStarted()) logger.error("Tried to initialize a started lobby", new IllegalAccessException());
        started = true;
        gameState = new GameState(connectionMap.keySet());
        gameController = new GameController(gameState, this);
        for (String nickname : connectionMap.keySet()) {
            remoteViews.add(new RemoteView(nickname, connectionMap.get(nickname)));
        }
        for (RemoteView v : remoteViews) {
            gameController.addObserver(v);
            gameController.getTurn().addObserver(v);
            v.addObserver(gameController);
        }
        logger.info("Started lobby ID=" + id);
        gameController.start(); //Starts the game.
    }

    public boolean isStarted() {
        return started;
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void removePlayer(String username) {
        if (!isStarted()) {
            connectionMap.remove(username);
            logger.info("Removed " + username + " form not started lobby ID=" + id);
        } else {
            logger.fatal("Trying to remove a player from a started lobby without close lobby");
            throw new IllegalStateException();
        }
    }
}
