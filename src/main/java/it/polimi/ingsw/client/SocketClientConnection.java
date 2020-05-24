package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import org.apache.log4j.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;


public class SocketClientConnection {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12345;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Socket socket;
    private final Controller controller;
    private boolean active = true;
    private final Logger logger = Logger.getLogger("ClientController");

    private final Queue<Options> toBeHandled = new LinkedList<>();

    public SocketClientConnection(Controller controller) throws IOException {
        this.controller = controller;
        this.socket = new Socket(IP, PORT);
        System.out.println("Connection established"); //TODO implements a method in the controller to print a string on ClientViews
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
                    if (inputObject instanceof Options) {
                        synchronized (toBeHandled) {
                            toBeHandled.add((Options) inputObject);
                            toBeHandled.notifyAll();
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (EOFException e) {
                controller.getClientView().showMessage("Connection Server-side has been closed, you opponents are gone");
            } catch (SocketException e) {
                controller.getClientView().showMessage("Connection Server-side has been closed");
            } catch (Exception e) {
                e.printStackTrace();
                controller.getClientView().showMessage("Connection Server-side has been closed, maybe because you opponents are gone");
            }
        });
        t.start();
        return t;
    }

    public void asyncHandleOptions() {
        new Thread(() -> {
            synchronized (toBeHandled) {
                while (isActive()) {
                    while (toBeHandled.isEmpty()) {
                        try {
                            toBeHandled.wait();
                        } catch (InterruptedException e) {
                            logger.fatal(e.getMessage(), e);
                        }
                    }
                    try {
                        controller.handleOption(toBeHandled.poll());
                    } catch (InterruptedException e) {
                        logger.fatal(e.getMessage(), e);
                    }
                }
            }
        }).start();
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
        try {
            asyncHandleOptions();
            Thread t0 = asyncReadFromSocket();
            t0.join();
        } catch (InterruptedException e) {
            logger.warn("Interrupted", e);
            Thread.currentThread().interrupt();
        } finally {
            closeConnection();
        }
    }

    public void closeConnection() {
        if (!active) return;
        active = false;
        try {
            in.close();
            out.close();
            socket.close();
            controller.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
