package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.options.Options;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private final String ip;
    private final int port;
    private boolean active = true;
    private final Controller controller;

    protected Object sendActionLock = new Object();


    public Client(String ip, int port, Controller controller) {
        this.ip = ip;
        this.port = port;
        this.controller = controller;
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void setActive(boolean active) {
        this.active = active;
    }

    public Thread asyncReadFromSocket(final ObjectInputStream socketIn) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    Object inputObject = socketIn.readObject();
                    if (inputObject instanceof String) {
                        System.out.println((String) inputObject);
                    } else if (inputObject instanceof Options) {
                        System.out.println(((Options) inputObject).getPlayer().getWorkers());
                        //TODO non so se qua ci serve un nuovo thread per fare questo
                        controller.handleOptions((Options) inputObject);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }

    public Thread asyncWriteToSocket(final ObjectOutputStream socketOut) {
        Thread t = new Thread(() -> {
            try {
                while (isActive()) {
                    synchronized (sendActionLock) {
                        while (controller.getCurrentAction() == null) {
                            sendActionLock.wait();
                        }
                        if (controller.isValidAction()) {
                            socketOut.reset();
                            socketOut.writeObject(controller.getCurrentAction());
                            socketOut.flush();

                        } else {
                            controller.reportInvalidAction();
                        }
                        controller.setCurrentAction(null);
                    }

                }
            } catch (Exception e) {
                setActive(false);
            }
        });
        t.start();
        return t;
    }


    public void run() throws IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Connection established");
        ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
        Scanner stdin = new Scanner(System.in);

        try {
            Thread t0 = asyncReadFromSocket(socketIn);
            Thread t1 = asyncWriteToSocket(socketOut);
            //TODO ci serve un altro thread per gestire la Cli o la Gui
            t0.join();
            t1.join();
        } catch (InterruptedException | NoSuchElementException e) {
            System.out.println("Connection closed from the Client side");
        } finally {
            stdin.close();
            socketIn.close();
            socketOut.close();
            socket.close();
        }
    }


}
