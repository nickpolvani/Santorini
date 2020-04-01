package it.polimi.ingsw.model.god;

import it.polimi.ingsw.bean.options.ConfirmOptions;
import it.polimi.ingsw.model.Tile.IndexTile;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Prometheus extends God {


    /**
     * Default constructor
     */
    protected Prometheus() {
        super(GodNameAndDescription.PROMETHEUS);
    }

    @Override
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    /*Non ho fatto l'override dei metodi tileTO... perché lui cambia il comportamento del worker quando builda non cambia dove può farlo*/
    @Override
    public void move(IndexTile indexTile) {
        super.move(indexTile);
    }

    @Override
    public void build(IndexTile indexTile) {
        super.build(indexTile);
    }
}