package it.polimi.ingsw.observer;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.TileOptions;

public interface ObserverOptions {
    void update(TileOptions t);

    void update(ConfirmOptions c);

    void update(GodOptions g);
}
