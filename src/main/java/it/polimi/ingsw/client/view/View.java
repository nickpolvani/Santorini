package it.polimi.ingsw.client.view;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.observer.Observable;


public abstract class View extends Observable<String> {

    private String nickname;
    private Options currentOption;

    /**
     * Used because GUI needs to know the currentOption for graphics
     *
     * @param currentOption
     */
    public void setCurrentOption(Options currentOption) {
        this.currentOption = currentOption;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (this.nickname != null && !this.nickname.isEmpty()) throw new IllegalStateException();
        this.nickname = nickname;
    }

    /**
     * Method used to show message to the players
     */
    public abstract void showMessage(String message);

    /**
     * Method used to show the island board current state to the players
     */
    public abstract void printBoard(IslandBoard board);

    /**
     * the process who read inputs of the players
     */
    public abstract void readInput();

    /**
     * Method used to start the view
     */
    public abstract void start();

    /**
     * Method used to close the view
     */
    public abstract void close();
}
