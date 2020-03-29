package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Bean.Choice.TileChoice;
import it.polimi.ingsw.Bean.Options.TileOptions;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Model.Worker;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Apollo extends God {

    /**
     * Default constructor
     */
    protected Apollo() {
    }

    /**
     * @param w
     */
    public void move(Worker w, Tile t) {
        // TODO implement here
    }

    @Override
    public void applyPlayerChoice(TileChoice tileChoice) {
        super.applyPlayerChoice(tileChoice);
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }

    /**
     * @param w
     */
    private void force(Worker w, Tile t) {
        // TODO implement here
    }

    /**
     * @param t
     * @return
     */
    protected Collection<Tile> tileToMove(Tile t) {
        // TODO implement here
        return null;
    }

}