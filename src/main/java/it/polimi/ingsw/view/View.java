package it.polimi.ingsw.view;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observable;
import it.polimi.ingsw.observer.Observer;

public abstract class View extends Observable<Action> implements Observer<Options> {
    private final Player player;

    protected View(Player player) {
        this.player = player;
    }

    protected Player getPlayer() {
        return player;
    }

    protected abstract void showMessage(Object message);

    public void reportError(String message) {
        showMessage(message);
    }

    /*
     *Quando arriva un messaggio crea la action e fa la notify
     */
}
