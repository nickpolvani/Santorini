package it.polimi.ingsw.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    protected final List<Observer<T>> observers = new ArrayList<>();

    public void addObserver(Observer<T> observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void addObserverList(List<Observer<T>> observerList) {
        synchronized (observerList) {
            synchronized (observers) {
                for (Observer<T> o : observerList) {
                    addObserver(o);
                }
            }
        }
    }

    public void notify(T message) {
        synchronized (observers) {
            for (Observer<T> observer : observers) {
                observer.update(message);
            }
        }
    }

    public void clearObserver() {
        synchronized (observers) {
            observers.clear();
        }
    }
}