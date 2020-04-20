package it.polimi.ingsw.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.view.RemoteView;
import it.polimi.ingsw.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Lobby {
    public final int size;
    private final Server server;
    private final List<View> remoteViews = new ArrayList<>();
    private final Map<String, ClientConnection> connectionMap = new LinkedHashMap<>();
    private boolean full = false;
    private GameState gameState;
    private GameController gameController;

    public Lobby(int size, Server server) {
        this.size = size;
        this.server = server;
    }

    Map<String, ClientConnection> getConnectionMap() {
        return connectionMap;
    }

    boolean isFull() {
        return full;
    }

    //Method to close the lobby
    //TODO write javadoc
    void close() {
        for (View w : remoteViews) {
            w.getClientConnection().closeConnection();
            server.removeRegisteredUsers(w.getNickname(), w.getClientConnection());
        }
    }

    void addClient(String name, ClientConnection clientConnection) {
        this.connectionMap.put(name, clientConnection);
        if (connectionMap.size() == size) full = true;
    }


    void init() {
        gameState = new GameState(connectionMap.keySet());
        gameController = new GameController(gameState);
        for (String s : connectionMap.keySet()) {
            remoteViews.add(new RemoteView(s, connectionMap.get(s)));
        }
        for (View v : remoteViews) {
            v.addObserver(gameController);
        }
        gameController.init(); //TODO questo avvia la vera è propria partita.
    }
}
