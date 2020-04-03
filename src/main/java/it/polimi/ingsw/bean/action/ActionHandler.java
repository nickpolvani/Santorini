package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.exception.AlreadyOccupiedException;

public class ActionHandler {
    public synchronized void start(Action a) throws AlreadyOccupiedException {
        a.run();
    }
}
