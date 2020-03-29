package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Bean.Options.*;

public interface ObserverOptions {
    void update(TileOptions t);
    void update(ConfirmOptions c);
    void update(GodOptions g);
}
