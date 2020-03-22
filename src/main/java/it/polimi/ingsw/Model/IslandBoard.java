package it.polimi.ingsw.Model;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class IslandBoard {

    /**
     * Default constructor
     */
    private IslandBoard() {
    }

    /**
     * 
     */
    private Tile board[][];

    /**
     * 
     */
    private static IslandBoard instance;


    /**
     * @return
     */
    public static IslandBoard getInstance() {
        // TODO implement here
        return null;
    }

    /**
     * @param tile 
     * @return
     */
    public Collection<Tile> neighbouringTiles(Tile tile) {
        // TODO implement here
        return null;
    }

    /**
     * @param worker 
     * @param newPosition
     */
    public void changePosition(Worker worker, Tile newPosition) {
        // TODO implement here
    }

    /**
     * @param tile
     */
    public void buildOnTile(Tile tile) {
        // TODO implement here
    }

    /**
     * @param tile
     */
    public void buildDome(Tile tile) {
        // TODO implement here
    }

}