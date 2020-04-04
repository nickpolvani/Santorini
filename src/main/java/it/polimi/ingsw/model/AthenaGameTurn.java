package it.polimi.ingsw.model;

/**
 * Type of Turn used when one of the players uses Athena
 */
public class AthenaGameTurn extends GameTurn {
    private boolean canMoveUp;


    public AthenaGameTurn(GameState gameState, Player currentPlayer) {
        super(gameState, currentPlayer);
        this.canMoveUp = true;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }
}
