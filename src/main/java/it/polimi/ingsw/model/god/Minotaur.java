package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.TileOptions;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Minotaur extends God {

    /**
     * Default constructor
     */
    protected Minotaur() {
        super(GodNameAndDescription.MINOTAUR);
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }

    @Override
    protected Collection<IndexTile> tileToMove(IndexTile indexTile) {
        return super.tileToMove(indexTile);
    }

    @Override
    public void move(IndexTile indexTile) {
        super.move(indexTile);
    }

    private void force(IndexTile indexTile) {
        // TODO implement here
    }
}