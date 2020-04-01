package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public abstract class God {

    private final GodNameAndDescription nameAndDescription;
    private GameState gameState;
    private Boolean hasMove;
    private Boolean hasBuild;

    /**
     * Default constructor, can be called only by GodsFactory
     */

    public God(GodNameAndDescription nameAndDescription) {
        this.nameAndDescription = nameAndDescription;
    }

    public GodNameAndDescription getNameAndDescription() {
        return nameAndDescription;
    }

    public Boolean getHasMove() {
        return hasMove;
    }

    public void setHasMove(Boolean hasMove) {
        this.hasMove = hasMove;
    }

    public Boolean getHasBuild() {
        return hasBuild;
    }

    public void setHasBuild(Boolean hasBuild) {
        this.hasBuild = hasBuild;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    protected Collection<IndexTile> tileToMove(IndexTile tile) {
        // TODO implement here
        return null;
    }

    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    protected Collection<IndexTile> tileToBuild(IndexTile tile) {
        // TODO implement here
        return null;
    }

    public void build(IndexTile indexTile) {
        // TODO implement here
    }


    // TODO scusate ma cosa fa?
    public Boolean isTurnOver() {
        return null;
    }

    public TileOptions createTileOptions() {
        return null;
    }

    public ConfirmOptions createConfirmOptions() {
        return null;
    }

    @Override
    public String toString() {
        return "Your god is " + this.nameAndDescription.getName() + "his power is:\n" + nameAndDescription.getDescriptionOfPower();
    }
}