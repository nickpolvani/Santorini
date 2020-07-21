package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.action.SelectNicknameAction;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.bean.ping.AckPacket;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
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

/**
 * The RemoteView manages communication via server-side sockets
 */
public class RemoteView extends Observable<GameAction> implements Observer<Options>, Runnable {

    private final Socket socket;
    private final Logger logger = Logger.getLogger("Server");
    private final Queue<Options> toSend = new LinkedList<>();
    private ObjectOutputStream out;
    private String username;
    private boolean active = false;
    private Timer timer = null;

    public RemoteView(Socket socket) {
        this.socket = socket;
    }

    private synchronized boolean isActive() {
        return active;
    }

    /**
     * Method used to send objects to the client
     *
     * @param message Object to send
     */
    private synchronized void send(final Object message) {
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
    public synchronized void closeConnection() {
        if (socket.isClosed()) return;
        if (timer != null) timer.cancel();
        active = false;
        try {
            out.flush();
            socket.close();
        } catch (IOException e) {
            logger.error("Error when closing socket!");
        }
        logger.debug((username != null ? username : "Unregistered user") + " socket closure PORT=" + socket.getPort());
        if (username != null) {
            //Null nickname means that user has never been registered on the server, Therefore, closing the connection is enough
            Server.getInstance().removePlayer(username);
        }
    }

    /**
     * Method used to send messages to the client asynchronously to the caller
     *
     * @param message object to be sent
     */
    public void asyncSend(final Options message) {
        if (!isActive()) return;
        synchronized (toSend) {
            toSend.add(message);
            toSend.notifyAll();
        }
    }

    private void startAsyncSend() { //TODO controllare con attenzione la sincronizzazione
        new Thread(() -> {
            while (isActive()) {
                Options a;
                synchronized (toSend) {
                    while (toSend.isEmpty()) {
                        try {
                            toSend.wait();
                        } catch (InterruptedException e) {
                            logger.fatal(e.getMessage(), e);
                            Thread.currentThread().interrupt();
                        }
                    }
                    a = toSend.poll();
                }
                send(a);
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
                if (nickname.isEmpty() || !Server.getInstance().addRegisteredUsers(nickname)) {
                    send(new SetupOptions(nickname, MessageType.NICKNAME_ALREADY_SET, Operation.SELECT_NICKNAME));
                } else {
                    send(new SetupOptions(nickname, MessageType.NICKNAME_APPROVED, Operation.MESSAGE_NO_REPLY));
                    nicknameApproved = true;
                }
            } while (!nicknameApproved);

            startAsyncSend();

            username = ((SelectNicknameAction) read).getNickname();
            logger.debug("Player's username on socket with port=" + socket.getPort() + " is: " + username);
            Server.getInstance().insertIntoLobby(this, in);
            handlerAck();
            while (isActive()) {
                Object o = in.readObject();
                if (o instanceof AckPacket) {
                    handlerAck();
                } else {
                    try {
                        notify((GameAction) o);
                    } catch (ClassCastException ignored) {
                        logger.error("An object has arrived that is not a instance of action", new IllegalArgumentException());
                    }
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage() + " of RemoteView USERNAME=" + username + " PORT=" + socket.getPort());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        } finally {
            if (!socket.isClosed()) closeConnection();
        }
    }

    /**
     * @return A timerTask which will be scheduled by the server
     * to close connection on the socket if it does not receive an ack message.
     */
    private TimerTask timerTaskCloseConnection() {
        return new TimerTask() {
            @Override
            public void run() {
                logger.info("Timer response of " + username + " is expired");
                closeConnection();
            }
        };
    }

    /**
     * @return A timerTask which will be scheduled by the server
     * to send an ack message on socket.
     */
    private TimerTask timerTaskSendAck() {
        return new TimerTask() {
            @Override
            public void run() {
                send(new AckPacket());
                timer.schedule(timerTaskCloseConnection(), 30000);
            }
        };
    }

    /**
     * Method used to handle received ack message
     */
    private void handlerAck() {
        logger.debug("Ack arrived on RemoteView of " + username);
        if (timer != null) timer.cancel();
        timer = new Timer();
        timer.schedule(timerTaskSendAck(), 10000);
    }

    @Override
    public void update(Options message) {
        asyncSend(message);
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }
}
