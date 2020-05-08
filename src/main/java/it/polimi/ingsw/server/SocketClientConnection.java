package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.observer.Observable;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends Observable<Action> implements ClientConnection, Runnable {

    // Questa è la socket per comunicare al Client
    private final Socket socket;
    private final Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final Logger logger = Logger.getLogger("Server");
    private String username;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
        super();
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public synchronized void closeConnection() { //Questo avvisa della chiusura il Client
        if (isActive()) {
            send("Connection closed!");
        }
        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        logger.debug("Closing SocketClientConnection of " + username + " PORT=" + socket.getPort());
    }

    private void close() {
        if (isActive()) {
            active = false;
            server.removePlayer(username);
        }
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        Object read;
        boolean b;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            send("Welcome!\nWhat is your username?");
            do {
                read = in.readObject();
                if (!(read instanceof String)) throw new IllegalArgumentException();
                if (server.getRegisteredUsers().containsKey(read)) {
                    b = true;
                    send("The chosen username is already in use!\nTry again: ");
                } else {
                    b = false;
                }
            } while (b);
            username = (String) read;
            server.addRegisteredUsers(username, this);
            send("Ok, your username is: " + username);
            logger.debug("Player's username on socket with port=" + socket.getPort() + " is: " + username);
            server.insertIntoLobby(username, this, in);
            while (isActive()) {
                Object o = in.readObject();
                if (!(o instanceof Action))
                    throw new IllegalArgumentException("An object has arrived that is not a instance of action");
                notify((Action) o);
            }
        } catch (IOException e) {
            logger.warn(e.getMessage() + " of SocketClientConnection USERNAME=" + username + " PORT=" + socket.getPort(), e);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }
}
