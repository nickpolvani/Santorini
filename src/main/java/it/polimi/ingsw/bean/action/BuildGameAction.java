package it.polimi.ingsw.bean.action;


import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Tile.IndexTile;

public class BuildGameAction extends IndexTileGameAction {

    /**
     * Default constructor
     *
     * @param indexTile The tile's index chosen to build on.
     * @param nickname  The player's nickname who generated the action.
     */
    public BuildGameAction(IndexTile indexTile, String nickname) {
        super(indexTile, nickname);
    }

    /**
     * @param operation It's the current operation in Turn
     * @return True if the operation passed like parameter is BUILD
     * @see GameAction
     */
    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.BUILD;
    }

    /**
     * Run the build using the player god build method.
     *
     * @throws DomeAlreadyPresentException If on the tile chosen by player there is already present a dome
     */
    @Override
    void execute() throws DomeAlreadyPresentException {
        this.getPlayer().getGod().build(this.indexTile);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BuildGameAction)) return false;
        return super.equals(obj);
    }
}
