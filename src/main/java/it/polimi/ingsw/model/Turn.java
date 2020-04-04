package it.polimi.ingsw.model;

public interface Turn {
    public Player getCurrentPlayer();

    public void switchTurn();

    public Operation getCurrentOperation();

    public void endCurrentOperation();
}
