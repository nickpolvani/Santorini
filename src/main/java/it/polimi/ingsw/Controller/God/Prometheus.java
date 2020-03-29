package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Bean.Choice.ConfirmChoice;
import it.polimi.ingsw.Bean.Choice.TileChoice;
import it.polimi.ingsw.Bean.Options.ConfirmOptions;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Model.Worker;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Prometheus extends God {

    @Override
    public void applyPlayerChoice(ConfirmChoice c) {
        super.applyPlayerChoice(c);
    }

    @Override
    public void applyPlayerChoice(TileChoice tileChoice) {
        super.applyPlayerChoice(tileChoice);
    }

    @Override
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    /**
     * Default constructor
     */
    protected Prometheus() {
    }

    /**
     * @param w
     */
    public void move(Worker w, Tile t) {
        // TODO implement here
    }

    /**
     * @param w
     */
    public void build(Worker w, Tile t) {
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

    /**
     * @param t
     * @return
     */
    protected Collection<Tile> tileToBuild(Tile t) {
        // TODO implement here
        return null;
    }

}