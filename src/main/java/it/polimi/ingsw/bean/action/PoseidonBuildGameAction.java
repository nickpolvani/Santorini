package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.exception.DomeAlreadyPresentException;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.Poseidon;

import java.util.Objects;

/**
 * action used to perform a build operation when the player owns Poseidon
 */
public class PoseidonBuildGameAction extends BuildGameAction {
    private final int level;

    public PoseidonBuildGameAction(Tile.IndexTile indexTile, int level, String nickname) {
        super(indexTile, nickname);
        this.level = level;
    }


    @Override
    public Boolean isCompatible(Operation operation) {
        return operation == Operation.POSEIDON_BUILD;
    }


    @Override
    void execute() throws DomeAlreadyPresentException {
        ((Poseidon) this.getPlayer().getGod()).build(this.indexTile, level);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PoseidonBuildGameAction)) return false;
        return super.equals(obj) && level == ((PoseidonBuildGameAction) obj).level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), level);
    }
}
