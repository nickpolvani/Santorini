package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import org.apache.log4j.Logger;

public class RemoteView extends Observable<Action> implements Observer<Options> {
    private final ClientConnection clientConnection;
    private final String nickname;

    public RemoteView(String nickname, ClientConnection clientConnection) {
        this.nickname = nickname;
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

    private class ActionReceiver implements Observer<Action> {
        private final Logger logger = Logger.getLogger("Server");

        @Override
        public void update(Action action) {
            logger.debug("Received: " + action);
            RemoteView.this.notify(action);
        }
    }
}
