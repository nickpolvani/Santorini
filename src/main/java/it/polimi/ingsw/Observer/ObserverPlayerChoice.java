package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Bean.Choice.*;

public interface ObserverPlayerChoice {
    void update(TileChoice t);
    void update(ConfirmChoice c);
    void update(GodChoice g);
}
