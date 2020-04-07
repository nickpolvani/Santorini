package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;

public interface Turn {
    Player getCurrentPlayer();

    void switchTurn();

    Operation getCurrentOperation();

    void endCurrentOperation();

    void addObserver(Observer<Options> message);

}
