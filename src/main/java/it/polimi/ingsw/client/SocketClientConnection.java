package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.ping.AckPacket;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;


/**
 * class used to handle connection with server
 */
public class SocketClientConnection {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Socket socket;
    private final Controller controller;
    private final Logger logger = Logger.getLogger("Client");
    private final Queue<Options> toBeHandled = new LinkedList<>();
    private boolean active = true;
    private Timer timerResponse = new Timer();

    public SocketClientConnection(Controller controller) throws IOException {
        this.controller = controller;
        this.socket = new Socket(IP, PORT);
        controller.getClientView().showMessage("Connection established");
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket() {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = in.readObject();
                    if (inputObject instanceof AckPacket) {
                        handlerAck();
                    } else if (inputObject instanceof Options) {
                        synchronized (toBeHandled) {
                            toBeHandled.add((Options) inputObject);
                            toBeHandled.notifyAll();
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (EOFException e) {
                controller.getClientView().showMessage("Connection Server-side has been closed");
            } catch (SocketException e) {
                controller.getClientView().showMessage("Connection closed");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                controller.reset();
            }
        });
        t.start();
        return t;
    }

    /**
     * @return Timer Task which is scheduled by the client to close the connection if it does not receive ack
     */
    private TimerTask timerTaskCloseConnection() {
        return new TimerTask() {
            @Override
            public void run() {
                closeConnection();
            }
        };
    }


    /**
     * @return The thread which handles options received from the server
     */
    public Thread asyncHandleOptions() {
        Thread optionsHandler = new Thread(() -> {
            Options tmpOptions;
            while (isActive()) {
                synchronized (toBeHandled) {
                    while (toBeHandled.isEmpty()) {
                        try {
                            toBeHandled.wait();
                        } catch (InterruptedException e) {
                            logger.fatal(e.getMessage());
                            Thread.currentThread().interrupt();
                            break;
                        }
                    }
                    tmpOptions = toBeHandled.poll();
                }
                try {
                    if (tmpOptions != null) controller.handleOption(tmpOptions);
                } catch (InterruptedException e) {
                    logger.fatal(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
            }
        });
        optionsHandler.start();
        return optionsHandler;
    }


    public void asyncWriteToSocket(final Action gameAction) {
        new Thread(() -> writeToSocket(gameAction)).start();
    }

    public synchronized void writeToSocket(final Object o) {
        try {
            if (isActive()) {
                out.reset();
                out.writeObject(o);
                out.flush();
            }
        } catch (Exception e) {
            closeConnection();
        }
    }

    public void run() {
        Thread t0 = asyncReadFromSocket();
        Thread t1 = asyncHandleOptions();
        try {
            t0.join();
            t1.join();
        } catch (InterruptedException e) {
            logger.warn("Interrupted");
            t1.interrupt();
        } finally {
            closeConnection();
        }
    }

    /**
     * Used to close Connection if any exception is thrown
     */
    public void closeConnection() {
        if (!active) return;
        active = false;
        try {
            socket.close();
            controller.reset();
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * this method handles the ack message received from server
     */
    private void handlerAck() {
        writeToSocket(new AckPacket());
        timerResponse.cancel();
        timerResponse = new Timer();
        timerResponse.schedule(timerTaskCloseConnection(), 30000);
    }
}
