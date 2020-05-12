package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.LobbySizeAction;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.WithoutPlayerOptions;
import it.polimi.ingsw.controller.Operation;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 12345;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final Map<String, ClientConnection> registeredUsers = new LinkedHashMap<>();
    private final Collection<Lobby> lobbiesInProgress = new ArrayList<>();
    private final Logger logger = Logger.getLogger("Server");
    private Lobby openLobby;
    private int currentLobbyNum = 0;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public Lobby getOpenLobby() {
        synchronized (openLobby) {
            return openLobby;
        }
    }

    boolean thereIsAOpenLobby() {
        return openLobby != null && !openLobby.isFull();
    }

    void createNewLobby(ClientConnection clientConnection, String name, ObjectInputStream in) throws IOException, ClassNotFoundException {
        //perhaps the asynchronous sending method can be a problem

        clientConnection.asyncSend(new WithoutPlayerOptions(name, Options.MessageType.CHOOSE_LOBBY_SIZE, Operation.SELECT_LOBBY_SIZE));
        boolean b;
        int numberPlayers;
        do {
            Object read = in.readObject();
            if (!(read instanceof LobbySizeAction)) throw new IllegalArgumentException();
            numberPlayers = ((LobbySizeAction) read).getLobbySize();
            if (numberPlayers != 2 && numberPlayers != 3) {
                b = true;
                clientConnection.asyncSend(new WithoutPlayerOptions(name, Options.MessageType.NOT_ALLOWED.setMessage("Numero inserito non corretto!\nRiprovaci"), Operation.SELECT_LOBBY_SIZE)); //TODO traduci
            } else {
                b = false;
            }
        } while (b);
        if (!thereIsAOpenLobby()) {
            openLobby = new Lobby(numberPlayers, currentLobbyNum);
            logger.info("Created a new openLobby ID=" + currentLobbyNum + " SIZE=" + numberPlayers);
            currentLobbyNum += 1;
        } else {
            throw new IllegalStateException();
        }
    }

    synchronized void insertIntoLobby(String name, ClientConnection clientConnection, ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (!thereIsAOpenLobby()) {
            createNewLobby(clientConnection, name, in);
        }
        if (openLobby.isFull())
            logger.error("Insertion into a full lobby", new IllegalAccessException("Cannot call this method is the lobby is full"));
        openLobby.addClient(name, clientConnection);
        if (openLobby.isStarted() || openLobby.isFull()) {
            lobbiesInProgress.add(openLobby);
            openLobby = null;
        }
    }

    Map<String, ClientConnection> getRegisteredUsers() {
        return registeredUsers;
    }

    void addRegisteredUsers(String name, ClientConnection clientConnection) {
        logger.debug("Registered username " + name);
        registeredUsers.put(name, clientConnection);
    }

    void removeRegisteredUsers(String username) {
        if (!registeredUsers.containsKey(username))
            logger.warn("Tried to delete an unregistered username");
        registeredUsers.remove(username);
        logger.info("Deleted username " + username);
    }

    Lobby findLobby(String username) {
        if (openLobby != null && openLobby.getConnectionMap().containsKey(username)) return openLobby;
        for (Lobby l : lobbiesInProgress) {
            if (l.getConnectionMap().containsKey(username)) {
                return l;
            }
        }
        logger.warn("Searched for a lobby that contains username: " + username + " but not found");
        return null;
    }

    public void removePlayer(String username) {
        Lobby tmp = findLobby(username);
        if (tmp != null && tmp.isStarted()) { //caso generico
            closeLobby(tmp);
        } else if (openLobby != null) { //caso in cui la lobby stata creata ma non inizializzata
            openLobby.removePlayer(username);
        } else { //caso quando la connesione cade prima di definire la prima lobby
            removeRegisteredUsers(username);
        }
    }

    public void closeLobby(Lobby lobby) {
        for (String n : lobby.getConnectionMap().keySet()) {
            removeRegisteredUsers(n);
        }
        lobby.close();
        lobbiesInProgress.remove(lobby);
    }

    public void run() {
        Logger logger = Logger.getLogger("Server");
        logger.info("Server started");
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                logger.info("New connection is active on PORT=" + newSocket.getPort());
                SocketServerConnection socketConnection = new SocketServerConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}