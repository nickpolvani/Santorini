package it.polimi.ingsw.server;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.Assert.*;

public class LobbyTest {

    static Server server;
    Lobby lobby;

    @BeforeClass
    public static void setUp() {
        server = Server.getInstance();
    }

    @After
    public void tearDown() {
        lobby = null;
    }

    @Test
    public void start() throws IOException {
        lobby = new Lobby(3, 1);
        RemoteView juri = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        juri.setUsername("juri");
        lobby.addClient(juri);
        assertTrue(lobby.containsUser("juri"));
        try {
            lobby.start();
            fail();
        } catch (IllegalStateException ignored) {
        }
        RemoteView nick = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        nick.setUsername("nick");
        lobby.addClient(nick);
        assertTrue(lobby.containsUser("nick"));
        try {
            lobby.start();
            fail();
        } catch (IllegalStateException ignored) {
        }
        RemoteView fra = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        fra.setUsername("fra");
        lobby.addClient(fra);
        try {
            lobby.start();
            fail();
        } catch (IllegalStateException ignored) {
        }
    }

    @Test
    public void removePlayer() throws IOException {
        lobby = new Lobby(3, 1);
        RemoteView juri = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        juri.setUsername("juri");
        lobby.addClient(juri);
        assertTrue(lobby.containsUser("juri"));
        RemoteView nick = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        nick.setUsername("nick");
        lobby.addClient(nick);
        assertTrue(lobby.containsUser("nick"));
        lobby.removePlayer("nick");
        assertFalse(lobby.containsUser("nick"));
        lobby.addClient(nick);
        RemoteView fra = new RemoteView(new Socket("127.0.0.1", Server.PORT));
        fra.setUsername("fra");
        lobby.addClient(fra);
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
    public void addClient() {
        lobby = new Lobby(2, 3);
        RemoteView r = new RemoteView(new Socket());
        r.setUsername("juri");
        lobby.addClient(r);
        assertTrue(lobby.containsUser("juri"));
        try {
            lobby.addClient(new RemoteView(new Socket()));
            fail();
        } catch (IllegalArgumentException ignored) {
        }
        r = new RemoteView(new Socket());
        r.setUsername("fra");
        lobby.addClient(r);
        assertTrue(lobby.containsUser("fra"));
        assertTrue(lobby.isStarted());
        assertNotNull(lobby.getGameState());
        assertNotNull(lobby.getGameController());
        try {
            lobby.addClient(new RemoteView(new Socket()));
        } catch (IllegalStateException ignored) {
        }
    }
}