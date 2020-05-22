package it.polimi.ingsw.server;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.Socket;

import static org.junit.Assert.*;

public class LobbyTest {

    static Server server;
    Lobby lobby;

    @BeforeClass
    public static void setUp() throws Exception {
        server = new Server();
    }

    @After
    public void tearDown() {
        lobby = null;
    }

    @Test
    public void start() {
    }

    @Test
    public void removePlayer() {
        lobby = new Lobby(3, 1);
        lobby.addClient("juri", new SocketServerConnection(new Socket(), server));
        assertTrue(lobby.containsUser("juri"));
        lobby.addClient("nick", new SocketServerConnection(new Socket(), server));
        assertTrue(lobby.containsUser("nick"));
        lobby.removePlayer("nick");
        assertFalse(lobby.containsUser("nick"));
        lobby.addClient("nick", new SocketServerConnection(new Socket(), server));
        lobby.addClient("fra", new SocketServerConnection(new Socket(), server));
        assertTrue(lobby.isFull());
        assertTrue(lobby.isStarted());
        try {
            lobby.removePlayer("nick");
            fail();
        } catch (IllegalStateException ignored) {
        }
    }

    @Test
    public void constructor() {
        try {
            lobby = new Lobby(1, 1);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
        try {
            lobby = new Lobby(4, 1);
            fail();
        } catch (IllegalArgumentException ignored) {
        }
        lobby = new Lobby(2, 1);
        lobby = new Lobby(3, 1);
    }

    @Test
    public void close() {
    }

    @Test
    public void addClient() {
        lobby = new Lobby(2, 3);

        lobby.addClient("juri", new SocketServerConnection(new Socket(), server));
        assertTrue(lobby.containsUser("juri"));
        try {
            lobby.addClient("juri", new SocketServerConnection(new Socket(), server));
            fail();
        } catch (IllegalArgumentException ignored) {
        }
        lobby.addClient("fra", new SocketServerConnection(new Socket(), server));
        assertTrue(lobby.containsUser("fra"));
        assertTrue(lobby.isStarted());
        assertNotNull(lobby.getGameState());
        assertNotNull(lobby.getGameController());
        try {
            lobby.addClient("nick", new SocketServerConnection(new Socket(), server));
        } catch (IllegalStateException ignored) {
        }
    }
}