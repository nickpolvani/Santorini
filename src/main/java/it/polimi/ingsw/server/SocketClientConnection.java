package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketClientConnection extends Observable<Action> implements ClientConnection, Runnable {

    // Questa è la socket per comunicare al client
    private final Socket socket;
    private final Server server;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean active = true;

    public SocketClientConnection(Socket socket, Server server) {
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
    public synchronized void closeConnection() { //Questo avvisa della chiusura il client
        send("Connection closed!");
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Error when closing socket!");
        }
        active = false;
    }

    private void close() { //Questo avvia la chiusura sul server e stampa a video sul server
        System.out.println("Deregistering client...");
        server.findLobby(this).close();
        System.out.println("Done!");
    }

    @Override
    public void asyncSend(final Object message) {
        new Thread(() -> send(message)).start();
    }

    @Override
    public void run() {
        String name;
        Object read;
        int numberPlayers;
        boolean b = false;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            send("Welcome!\nWhat is your username?");
            do {
                read = in.readObject();
                if (!(read instanceof String)) throw new IllegalArgumentException();
                if (server.getRegisteredUsers().containsKey(read)) {
                    b = true;
                    send("L'username scelto è gia in uso! \n Inseriscine un altro:"); //TODO traduci
                }
            } while (b);
            name = (String) read;
            send("Ok, your username is: " + name);
            if (!server.thereIsAOpenLobby()) {
                send("Con quante persone vuoi giocare?"); //TODO traduci
                do {
                    read = in.readObject();
                    if (!(read instanceof String)) throw new IllegalArgumentException();
                    numberPlayers = Integer.parseInt((String) read);
                    if (numberPlayers != 2 && numberPlayers != 3) {
                        b = true;
                        send("Numero inserito non corretto!\n Riprovaci"); //TODO traduci
                    }
                } while (b);
                server.createNewLobby(name, this, numberPlayers);
            } else {
                server.insertIntoLobby(name, this);
            }
            while (isActive()) {
                Object o = in.readObject();
                if (!(o instanceof Action)) throw new IllegalArgumentException();
                notify((Action) o);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            close();
        }
    }
}
