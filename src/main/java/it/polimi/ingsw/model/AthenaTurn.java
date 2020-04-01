package it.polimi.ingsw.model;

public class AthenaTurn extends Turn {
    private boolean canMoveUp;

    public AthenaTurn(GameState myGame) {
        super(myGame);
        this.canMoveUp = true;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
}
