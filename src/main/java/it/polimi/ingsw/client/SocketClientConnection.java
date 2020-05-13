package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection {
    private final String IP = "127.0.0.1";
    private final int PORT = 12345;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final Socket socket;
    private final Controller controller;
    private boolean active = true;

    public SocketClientConnection(Controller controller) throws IOException {
        this.controller = controller;
        this.socket = new Socket(IP, PORT);
        System.out.println("Connection established");
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
                       // System.out.println(inputObject);
                        controller.handleOption((Options) inputObject);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public void asyncWriteToSocket(final Action gameAction) {
        Thread t = new Thread(() -> {
            writeToSocket(gameAction);
        });
        t.start();
    }

    public synchronized void writeToSocket(final Object o) {
        try {
            if (isActive()) {
                out.reset();
                out.writeObject(o);
                out.flush();
            }
        } catch (Exception e) {
            setActive(false);
        }
    }

    public void run() throws IOException {
        try {
            Thread t0 = asyncReadFromSocket();
            t0.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
            out.close();
            socket.close();
        }
    }
}