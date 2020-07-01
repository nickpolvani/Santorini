package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.LobbySizeAction;
import it.polimi.ingsw.bean.options.MessageOption;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.utilities.MessageType;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Server class manages the server-side network infrastructure, accepting new connections.
 * In addition, it takes care of multi-lobby management and user registration.
 * This class implements a Singleton pattern
 *
 * @see java.lang.Runnable
 */
public class Server implements Runnable {
    /**
     * Port number on which the server is waiting for new connections
     */
    public static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger("Server");
    private static Server instance;
    private final ServerSocket serverSocket;
    private final ExecutorService executor = Executors.newFixedThreadPool(128);
    private final Set<String> registeredUsers = new TreeSet<>();
    private final Collection<Lobby> lobbiesInProgress = new ArrayList<>();
    private Lobby openLobby;
    private int currentLobbyNum = 0;

    public Server() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        instance = this;
    }

    /**
     * Always returns the same instance of the server, the class implements the singleton pattern
     *
     * @return The server instance, null if there was a problem creating the instance
     */
    public static Server getInstance() {
        if (instance == null) {
            try {
                instance = new Server();
            } catch (IOException e) {
                logger.fatal("The server cannot be initialized MESSAGE: " + e.getMessage());
            }
        }
        return instance;
    }

    boolean thereIsAOpenLobby() {
        return openLobby != null && !openLobby.isFull();
    }

    /**
     * Used to insert a new player just logged into an already open lobby.
     *
     * @param name             The name of the player
     * @param clientConnection The clientConnection of the player that is used to communicate with the client
     * @param in               The objectInputStream of the player's socket
     * @throws IOException            Thrown if there is a problem with the ObjectInputStream object
     * @throws ClassNotFoundException Thrown if the object received on the inputStream is not recognized
     */
    synchronized void insertIntoLobby(String name, ClientConnection clientConnection, ObjectInputStream in) throws IOException, ClassNotFoundException {
        if (!thereIsAOpenLobby()) {
            createNewLobby(clientConnection, name, in);
        }
        if (openLobby.isFull())
            logger.error("Insertion into a full lobby", new IllegalAccessException("Cannot call this method if the lobby is full"));
        clientConnection.asyncSend(new MessageOption(name, ("Successfully added to a lobby." +
                " It has " + openLobby.size + " players." + " Wait until the lobby starts..")));
        openLobby.addClient(name, clientConnection);
        if (openLobby.isStarted() || openLobby.isFull()) {
            lobbiesInProgress.add(openLobby);
            openLobby = null;
        }
    }

    /**
     * Method used to create a new lobby on the server.
     * Communicate directly with the client about the size of the lobby.
     * The client that created the lobby is automatically inserted into the newly created lobby.
     *
     * @param clientConnection The clientConnection of the player that is used to communicate with the client
     * @param name             The name of the player
     * @param in               The objectInputStream of the player's socket
     * @throws IOException            Thrown if there is a problem with the ObjectInputStream object
     * @throws ClassNotFoundException Thrown if the object received on the inputStream is not recognized
     * @see Lobby
     */
    synchronized void createNewLobby(ClientConnection clientConnection, String name, ObjectInputStream in) throws IOException, ClassNotFoundException {
        clientConnection.asyncSend(new SetupOptions(name, MessageType.CHOOSE_LOBBY_SIZE, Operation.SELECT_LOBBY_SIZE));
        boolean b;
        int numberPlayers;
        do {
            Object read = in.readObject();
            if (!(read instanceof LobbySizeAction)) throw new IllegalArgumentException();
            numberPlayers = ((LobbySizeAction) read).getLobbySize();
            if (numberPlayers != 2 && numberPlayers != 3) {
                b = true;
                clientConnection.asyncSend(new SetupOptions(name, ("Inserted Number is not allowed. Please reinsert it!"), Operation.SELECT_LOBBY_SIZE));
            } else {
                b = false;
            }
        } while (b);
        if (thereIsAOpenLobby()) throw new IllegalStateException();
        openLobby = new Lobby(numberPlayers, currentLobbyNum);
        MessageOption mess = new MessageOption(name, "Lobby successfully created. Wait for other players");
        clientConnection.asyncSend(mess);
        logger.info("Created a new openLobby ID=" + currentLobbyNum + " SIZE=" + numberPlayers);
        ++currentLobbyNum;

    }

    /**
     * Used to register a new player on the server
     *
     * @param username The new username
     * @return Returns true if the registration is successful otherwise false
     * @see Lobby
     */
    synchronized boolean addRegisteredUsers(String username) {
        boolean approved = registeredUsers.add(username);
        if (approved) logger.debug("Registered username " + username);
        return approved;
    }

    /**
     * Used to unregister a player
     *
     * @param username The player's username
     * @see it.polimi.ingsw.model.Player
     */
    synchronized void unregisteredUsername(String username) {
        if (!registeredUsers.remove(username)) logger.warn("Tried to delete an unregistered username");
        logger.info("Deleted username " + username);
    }

    /**
     * Used to find a lobby based on a player's name.
     *
     * @param username The player's username.
     * @return Returns the lobby instance containing the player or null if no match was found.
     * @see Lobby
     * @see it.polimi.ingsw.model.Player
     */
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

    /**
     * Method used to remove a player. If the player has been assigned to a lobby,
     * it is closed and all players assigned to it are removed.
     *
     * @param username The player's username
     * @see Lobby
     * @see it.polimi.ingsw.model.Player
     */
    public void removePlayer(String username) {
        Lobby tmp = findLobby(username); //TODO rimuovi i commenti
        if (tmp != null) {
            if (tmp.isClose()) return;
            if (tmp.isStarted()) { //caso generico
                closeLobby(tmp);
            } else if (tmp.equals(openLobby)) { //caso in cui la lobby stata creata ma non inizializzata
                if (openLobby.getConnectionMap().size() == 1) {
                    openLobby = null;
                    logger.debug("Temporary lobby closed because only player left");
                } else {
                    openLobby.removePlayer(username);
                }
                unregisteredUsername(username);
            }
        } else { //caso quando la connesione cade prima di definire la prima lobby
            unregisteredUsername(username);
        }
    }

    /**
     * Method used to close and remove a lobby from the server.
     *
     * @param lobby The instance of the lobby
     * @see Lobby
     */
    public void closeLobby(Lobby lobby) {
        lobby.close();
        for (String n : lobby.getConnectionMap().keySet()) {
            unregisteredUsername(n);
        }
        lobbiesInProgress.remove(lobby);
    }

    @Override
    public void run() {
        logger.info("Server started");
        while (true) {
            try {
                Socket newSocket = serverSocket.accept();
                logger.info("New connection is active on PORT=" + newSocket.getPort());
                SocketServerConnection socketConnection = new SocketServerConnection(newSocket, this);
                executor.submit(socketConnection);
            } catch (IOException e) {
                logger.error(e.getMessage());
                break;
            }
        }
    }
}