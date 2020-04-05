package it.polimi.ingsw.view;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.server.ClientConnection;

public abstract class View extends Observable<Action> implements Observer<Options> {

    protected final String nickname;
    protected final ClientConnection clientConnection;

    protected View(String nickname, ClientConnection clientConnection) {
        this.nickname = nickname;
        this.clientConnection = clientConnection;
    }

    public String getNickname() {
        return nickname;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    protected abstract void showMessage(Object message);

    void handleMove(Action a) {
        notify(a);
    }

}
