package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Bean.Options.*;


import java.util.*;

public class ObservableOptions {
    private final List<ObserverOptions> observers = new ArrayList<>();

    public void addObserver(ObserverOptions observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void removeObserver(ObserverOptions observer){
        synchronized (observers){
            observers.add(observer);
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
