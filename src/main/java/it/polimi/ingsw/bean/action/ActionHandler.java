package it.polimi.ingsw.bean.action;

public class ActionHandler {
    public synchronized void start(Action a) {
        a.run();
    }
}
