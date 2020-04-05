package it.polimi.ingsw.server;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.observer.Observer;

public interface ClientConnection {

    void closeConnection();

    void addObserver(Observer<Action> observer);

    void asyncSend(Object message);
}
