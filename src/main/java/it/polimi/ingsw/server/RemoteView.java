package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.GameAction;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import org.apache.log4j.Logger;

public class RemoteView extends Observable<GameAction> implements Observer<Options> {
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

    public String getNickname() {
        return nickname;
    }

    private class ActionReceiver implements Observer<GameAction> {
        private final Logger logger = Logger.getLogger("Server");

        @Override
        public void update(GameAction gameAction) {
            logger.debug("Received: " + gameAction);
            RemoteView.this.notify(gameAction);
        }
    }
}
