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
public class Demeter extends God {

    @Override
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    @Override
    public void applyPlayerChoice(ConfirmChoice c) {
        super.applyPlayerChoice(c);
    }

    @Override
    public void applyPlayerChoice(TileChoice tileChoice) {
        super.applyPlayerChoice(tileChoice);
    }

    /**
     * Default constructor
     */
    protected Demeter() {
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
    protected Collection<Tile> tileToBuild(Tile t) {
        // TODO implement here
        return null;
    }

}