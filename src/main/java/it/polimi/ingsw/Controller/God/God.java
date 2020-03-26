package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Model.NameGod;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Model.Worker;

import java.util.Collection;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public abstract class God {

    /**
     *
     */
    private Player player;
    /**
     *
     */
    private NameGod name;

    /**
     * Default constructor
     */
    public God() {
    }

    /**
     * @param tile
     * @return
     */
    protected Collection<Tile> tileToMove(Tile tile) {
        // TODO implement here
        return null;
    }

    /**
     * @param tile
     * @return
     */
    protected Collection<Tile> tileToBuild(Tile tile) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Player getPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @param p
     */
    public void setPlayer(Player p) {
        // TODO implement here
    }

    /**
     * @return
     */
    public NameGod getName() {
        // TODO implement here
        return null;
    }

    /**
     * @param name
     */
    public void setName(NameGod name) {
        // TODO implement here
    }

    /**
     * @param w
     */
    public void move(Worker w) {
        // TODO implement here
    }

    /**
     * @param w
     */
    public void build(Worker w) {
        // TODO implement here
    }

}