package it.polimi.ingsw.observer;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.TileOptions;

import java.util.ArrayList;
import java.util.List;

public class ObservableOptions {
    private final List<ObserverOptions> observers = new ArrayList<>();

    public void addObserver(ObserverOptions observer){
        synchronized (observers){
            observers.remove(observer);
        }
    }

    public void removeObserver(ObserverOptions observer){
        synchronized (observers){
            observers.remove(observer);
        }
    }


    protected void notify(TileOptions t){
        synchronized (observers){
            for (ObserverOptions observer : observers){
                observer.update(t);
            }
        }
    }

    protected void notify(ConfirmOptions c){
        synchronized (observers){
            for(ObserverOptions observer : observers){
                observer.update(c);
            }
        }
    }

    protected void notify(GodOptions g){
        synchronized (observers){
            for(ObserverOptions observer : observers){
                observer.update(g);
            }
        }
    }

}
