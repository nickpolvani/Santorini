package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.observer.Observable;


public abstract class View extends Observable<String> {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (this.nickname != null && !this.nickname.isEmpty()) throw new IllegalStateException();
        this.nickname = nickname;
    }

    public abstract void showMessage(String message);

    public abstract void printBoard(IslandBoard board);

    public abstract void readInput();

    public abstract void start();

    public abstract void close();
}
