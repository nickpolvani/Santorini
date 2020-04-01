package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Artemis extends God {

    /**
     * Default constructor
     */
    protected Artemis() {
        super(GodNameAndDescription.ARTEMIS);
    }

    @Override
    public void move(IndexTile indexTile) {
        // TODO implement here
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile t) {
        // TODO implement here
        return null;
    }

    @Override
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }

}