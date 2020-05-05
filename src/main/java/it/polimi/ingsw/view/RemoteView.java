package it.polimi.ingsw.view;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;
import org.apache.log4j.Logger;

public class RemoteView extends View {

    public RemoteView(String nickname, ClientConnection c) {
        super(nickname, c);
        c.addObserver(new ActionReceiver());
    }

    @Override
    protected void showMessage(Object message) {
        getClientConnection().asyncSend(message);
    }

    @Override
    public void update(Options message) {
        getClientConnection().asyncSend(message);
    }

    private class ActionReceiver implements Observer<Action> {
        private final Logger logger = Logger.getLogger("Server");
        @Override
        public void update(Action action) {
            logger.debug("Received: " + action);
            handleMove(action);
        }
    }
}
