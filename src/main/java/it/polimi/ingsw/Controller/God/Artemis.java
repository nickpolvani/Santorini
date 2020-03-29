package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Bean.Choice.ConfirmChoice;
import it.polimi.ingsw.Bean.Choice.TileChoice;
import it.polimi.ingsw.Bean.Options.ConfirmOptions;
import it.polimi.ingsw.Bean.Options.TileOptions;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Model.Worker;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Artemis extends God {

    /**
     * Default constructor
     */
    protected Artemis() {
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
    public ConfirmOptions createConfirmOptions() {
        return super.createConfirmOptions();
    }

    @Override
    public TileOptions createTileOptions() {
        return super.createTileOptions();
    }

    @Override
    public void applyPlayerChoice(ConfirmChoice c) {
        super.applyPlayerChoice(c);
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