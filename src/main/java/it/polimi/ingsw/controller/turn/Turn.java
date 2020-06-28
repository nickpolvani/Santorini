package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.Start;


public interface Turn extends Start {
    Player getCurrentPlayer();

    /**
     * next player becomes the current player
     */
    void switchTurn();

    Operation getCurrentOperation();

    /**
     * terminates the current operation and begins the next one, in case it calls switchTurn()
     */
    void endCurrentOperation();

    void addObserver(Observer<Options> observer);
}
