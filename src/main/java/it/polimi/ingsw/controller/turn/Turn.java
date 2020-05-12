package it.polimi.ingsw.controller.turn;

import it.polimi.ingsw.bean.options.PlayerOptions;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.Start;

public interface Turn extends Start {
    Player getCurrentPlayer();

    void switchTurn();

    Operation getCurrentOperation();

    void endCurrentOperation();

    void addObserver(Observer<PlayerOptions> message);
}
