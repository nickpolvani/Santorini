package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Model.NameGods;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Tile;
import it.polimi.ingsw.Model.Worker;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public abstract class God {

    /**
     * Default constructor
     */
    public God() {
    }

    /**
     * 
     */
    private Player player;

    /**
     * 
     */
    private NameGods name;

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
     * @param p
     */
    public void setPlayer(Player p) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Player getPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @param name
     */
    public void setName(NameGods name) {
        // TODO implement here
    }

    /**
     * @return
     */
    public NameGods getName() {
        // TODO implement here
        return null;
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