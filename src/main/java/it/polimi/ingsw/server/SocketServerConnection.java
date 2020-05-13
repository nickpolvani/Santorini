package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.ChooseNicknameAction;
import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.observer.Observable;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketServerConnection extends Observable<GameAction> implements ClientConnection, Runnable {

    // Questa è la socket per comunicare al SocketClientConnection
    private final Socket socket;
    private final Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private final Logger logger = Logger.getLogger("Server");
    private String username;

    private boolean active = true;

    public SocketServerConnection(Socket socket, Server server) {
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
    public synchronized void closeConnection() { //Questo avvisa della chiusura il SocketClientConnection
        if (isActive()) {
            send("Connection closed!");
        }
        active = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        logger.debug("Closing SocketServerConnection of " + username + " PORT=" + socket.getPort());
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
        boolean nicknameApproved = false;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            send(new SetupOptions(null, Options.MessageType.CHOOSE_NAME, Operation.SELECT_NICKNAME));
            do {
                read = in.readObject();
                if (!(read instanceof ChooseNicknameAction)) throw new IllegalArgumentException();
                String nickname = ((ChooseNicknameAction) read).getNickname();
                if (server.getRegisteredUsers().containsKey(nickname)) {
                    send(new SetupOptions(nickname, Options.MessageType.NICKNAME_ALREADY_SET, Operation.SELECT_NICKNAME));
                } else {
                    send(new SetupOptions(nickname, Options.MessageType.NICKNAME_APPROVED, Operation.SELECT_NICKNAME));
                    nicknameApproved = true;
                }
            } while (!nicknameApproved);
            username = ((ChooseNicknameAction) read).getNickname();
            server.addRegisteredUsers(username, this);
            logger.debug("Player's username on socket with port=" + socket.getPort() + " is: " + username);
            server.insertIntoLobby(username, this, in);
            while (isActive()) {
                Object o = in.readObject();
                if (!(o instanceof GameAction))
                    throw new IllegalArgumentException("An object has arrived that is not a instance of action");
                notify((GameAction) o);
            }
        } catch (IOException e) {
            logger.warn(e.getMessage() + " of SocketServerConnection USERNAME=" + username + " PORT=" + socket.getPort(), e);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            close();
        }
    }
}
