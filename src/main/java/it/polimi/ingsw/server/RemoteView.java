package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import org.apache.log4j.Logger;

/**
 * Communication between the controller and clients takes place via this class.
 * It is an abstraction of client-side views. The controller, through the Observer pattern,
 * communicates with the remoteView, it will then be responsible for effective communication
 * with the clients
 *
 * @see Observable
 * @see it.polimi.ingsw.observer.Observer
 * @see GameAction
 * @see Options
 */
public class RemoteView extends Observable<GameAction> implements Observer<Options> {
    private final ClientConnection clientConnection;
    private final String username;

    /**
     * Default constructor
     *
     * @param username         The player's username
     * @param clientConnection The player's ClientConnection
     */
    public RemoteView(String username, ClientConnection clientConnection) {
        this.username = username;
        this.clientConnection = clientConnection;
        clientConnection.addObserver(new ActionReceiver());
    }

    @Override
    public void update(Options message) {
        getClientConnection().asyncSend(message);
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public String getUsername() {
        return username;
    }

    /**
     * ActionReceiver is a middle object between the clientConnection and the remoteView
     * which allows communication from the clientConnection to the remoteView
     */
    private class ActionReceiver implements Observer<GameAction> {
        private final Logger logger = Logger.getLogger("Server");

        @Override
        public void update(GameAction gameAction) {
            logger.debug("Received: " + gameAction);
            RemoteView.this.notify(gameAction);
        }
    }
}
