package it.polimi.ingsw.view;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

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
        clientConnection.asyncSend(message);
    }

    private class ActionReceiver implements Observer<Action> {
        //TODO la remoteView è un observer della ClientConnection. Quando arriva un messaggio la RemoteView viene notificata
        @Override
        public void update(Action ac) {
            System.out.println("Received: " + ac);
            handleMove(ac);
        }
    }
}
