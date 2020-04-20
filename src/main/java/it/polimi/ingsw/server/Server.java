package it.polimi.ingsw.server;

import java.io.IOException;
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
    private final Map<String, ClientConnection> registeredUsers = new LinkedHashMap<>(); //TODO questa forse non va bene map quando riceve una nuova coppia se ha gia in memoria la key soprascrive l'oggetto. Nooi vogliamo un avviso
    private final Collection<Lobby> lobbies = new ArrayList<>();
    private Lobby tmpLobby;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
    }

    public Lobby getTmpLobby() {
        return tmpLobby;
    }

    boolean thereIsAOpenLobby() {
        return tmpLobby != null && !tmpLobby.isFull();
    }

    synchronized void createNewLobby(String name, ClientConnection clientConnection, int dimLobby) throws IllegalArgumentException {
        if (!thereIsAOpenLobby()) {
            if (dimLobby != 2 && dimLobby != 3)
                throw new IllegalArgumentException("il massimo numero di giocatori e 3!!");
            tmpLobby = new Lobby(dimLobby, this);
            tmpLobby.addClient(name, clientConnection);
            addRegisteredUsers(name, clientConnection);
        } else {
            throw new IllegalStateException();
        }
    }

    synchronized void insertIntoLobby(String name, ClientConnection clientConnection) {
        if (tmpLobby.isFull())
            throw new IllegalStateException("Cannot call this method is the lobby is full");
        tmpLobby.addClient(name, clientConnection);
        addRegisteredUsers(name, clientConnection);
        if (tmpLobby.isFull()) {
            lobbies.add(tmpLobby);
            new Thread(() -> tmpLobby.init()).start();
            tmpLobby = null;
        }
    }

    Map<String, ClientConnection> getRegisteredUsers() {
        return registeredUsers;
    }

    void addRegisteredUsers(String name, ClientConnection clientConnection) {
        registeredUsers.put(name, clientConnection);
    }

    void removeRegisteredUsers(String name, ClientConnection clientConnection) {
        registeredUsers.remove(name);
    }

    Lobby findLobby(ClientConnection clientConnection) {
        Lobby lobby = null;
        for (Lobby l : lobbies) {
            if (l.getConnectionMap().containsValue(clientConnection)) {
                lobby = l;
                break;
            }
        }
        return lobby;
    }

    public void run() {
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                SocketClientConnection socketConnection = new SocketClientConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}