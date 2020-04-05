package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.exception.AlreadyOccupiedException;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;

public class ActionHandler {
    public synchronized void start(Action a) throws AlreadyOccupiedException, DomeAlreadyPresentException {
        a.run();
    }
}
