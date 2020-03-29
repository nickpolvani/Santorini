package it.polimi.ingsw.Observer;

import it.polimi.ingsw.Bean.Choice.*;

import java.util.*;


public class ObservablePlayerChoice {
    private final List<ObserverPlayerChoice> observers = new ArrayList<>();

    public void addObserver(ObserverPlayerChoice observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    public void removeObserver(ObserverPlayerChoice observer){
        synchronized (observers){
            observers.add(observer);
        }
    }

    protected void notify(TileChoice t){
        synchronized (observers) {
            for(ObserverPlayerChoice observer : observers){
                observer.update(t);
            }
        }
    }

    protected void notify(ConfirmChoice c){
        synchronized (observers) {
            for(ObserverPlayerChoice observer : observers){
                observer.update(c);
            }
        }
    }

    protected void notify(GodChoice g){
        synchronized (observers) {
            for(ObserverPlayerChoice observer : observers){
                observer.update(g);
            }
        }
    }

}
