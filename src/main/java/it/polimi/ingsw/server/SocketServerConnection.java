package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.action.SelectNicknameAction;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.bean.ping.AckPacket;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.utilities.MessageType;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class SocketServerConnection extends Observable<GameAction> implements ClientConnection, Runnable {

    private final Socket socket;
    private final Server server;
    private final Logger logger = Logger.getLogger("Server");
    private final Queue<Object> toSend = new LinkedList<>();
    private ObjectOutputStream out;
    private String username;
    private boolean active = false;
    private Timer timer = new Timer();

    public SocketServerConnection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    private synchronized boolean isActive() {
        return active;
    }

    /**
     * Method used to send objects to the client
     *
     * @param message Object to send
     */
    private synchronized void send(Object message) {
        try {
            out.reset();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * This method is used to close the connection and the server-side socket.
     * It also starts the deregistration of the nickname
     * and if necessary the closure of the lobby.
     */
    @Override
    public synchronized void closeConnection() {
        if (socket.isClosed()) return;
        active = false;
        try {
            out.flush();
            socket.close();
        } catch (IOException e) {
            logger.error("Error when closing socket!");
        }
        logger.debug(username + "socket closure PORT=" + socket.getPort());
        if (username != null) {
            //Null nickname means that user has never been registered on the server, Therefore, closing the connection is enough
            server.removePlayer(username);
        }
    }

    /**
     * Method used to send messages to the client asynchronously to the caller
     *
     * @param message
     */
    @Override
    public void asyncSend(final Object message) {
        if (!isActive()) return;
        synchronized (toSend) {
            toSend.add(message);
            toSend.notifyAll();
        }
    }

    private void startAsyncSend() {
        new Thread(() -> {
            synchronized (toSend) {
                while (isActive()) {
                    while (toSend.isEmpty()) {
                        try {
                            toSend.wait();
                        } catch (InterruptedException e) {
                            logger.fatal(e.getMessage(), e);
                            Thread.currentThread().interrupt();
                        }
                    }
                    send(toSend.poll());
                }
            }
        }).start();
    }

    @Override
    public void run() {
        active = true;
        Object read;
        boolean nicknameApproved = false;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            send(new SetupOptions(null, MessageType.CHOOSE_NAME, Operation.SELECT_NICKNAME));
            do {
                read = in.readObject();
                if (!(read instanceof SelectNicknameAction)) throw new IllegalArgumentException();
                String nickname = ((SelectNicknameAction) read).getNickname();
                if (nickname.isEmpty() || !server.addRegisteredUsers(nickname)) {
                    send(new SetupOptions(nickname, MessageType.NICKNAME_ALREADY_SET, Operation.SELECT_NICKNAME));
                } else {
                    send(new SetupOptions(nickname, MessageType.NICKNAME_APPROVED, Operation.MESSAGE_NO_REPLY));
                    nicknameApproved = true;
                }
            } while (!nicknameApproved);

            startAsyncSend();

            username = ((SelectNicknameAction) read).getNickname();
            logger.debug("Player's username on socket with port=" + socket.getPort() + " is: " + username);
            server.insertIntoLobby(username, this, in);
            send(new AckPacket());
            while (isActive()) {
                Object o = in.readObject();
                if (o instanceof AckPacket) {
                    send(new AckPacket());
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(newTimerResponse(), 30000);
                } else {
                    try {
                        notify((GameAction) o);
                    } catch (ClassCastException ignored) {
                        logger.error("An object has arrived that is not a instance of action", new IllegalArgumentException());
                    }
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage() + " of SocketServerConnection USERNAME=" + username + " PORT=" + socket.getPort());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } finally {
            if (!socket.isClosed()) closeConnection();
        }
    }

    private TimerTask newTimerResponse() {
        return new TimerTask() {
            @Override
            public void run() {
                logger.info("Timer response of " + username + " is expired");
                closeConnection();
            }
        };
    }
}
