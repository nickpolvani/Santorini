package it.polimi.ingsw.Controller.God;

import it.polimi.ingsw.Bean.Choice.ChoiceType;
import it.polimi.ingsw.Bean.Choice.ConfirmChoice;
import it.polimi.ingsw.Bean.Choice.TileChoice;
import it.polimi.ingsw.Bean.Options.ConfirmOptions;
import it.polimi.ingsw.Bean.Options.TileOptions;
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
    private Worker worker;
    private ChoiceType[] turnChoices;
    private int choiceNumber;

    /**
     * Default constructor, can be called only by GodsFactory
     */
    protected God() {
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
    public void move(Worker w, Tile t) {
        // TODO implement here
    }

    /**
     * @param w
     */
    public void build(Worker w, Tile t) {
        // TODO implement here
    }

    public Boolean isTurnOver() {
        return null;
    }

    public void applyPlayerChoice(TileChoice tileChoice) {
    }

    public void applyPlayerChoice(ConfirmChoice c) {
    }

    public TileOptions createTileOptions() {
        return null;
    }

    public ConfirmOptions createConfirmOptions() {
        return null;
    }

}