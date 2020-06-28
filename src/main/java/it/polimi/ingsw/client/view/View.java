package it.polimi.ingsw.client.view;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.observer.Observable;

/**
 * View is the user interface, that can be graphical or command-line
 */
public abstract class View extends Observable<String> {

    protected IslandBoard board;
    private String nickname;
    private Options currentOption;

    /**
     * Used because GUI needs to know the currentOption for graphics
     *
     * @param currentOption current options the player has
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

    public boolean isSameBoard(IslandBoard board) {
        return board.equals(this.board);
    }

    /**
     * Method used to show message to the players
     *
     * @param message string that player will see
     */
    public abstract void showMessage(String message);

    /**
     * Method used to show the island board current state to the players
     *
     * @param board instance (copy) of the game board
     */
    public void printBoard(IslandBoard board) {
        if (!isSameBoard(board)) {
            updateBoard(board);
        }
    }

    protected abstract void updateBoard(IslandBoard board);


    /**
     * Method used to start the view
     */
    public abstract void start();

    /**
     * Method used to close the view
     */
    public abstract void close();
}
