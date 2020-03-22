package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.God.God;

import java.util.*;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class GameState {

    /**
     * Default constructor
     */
    public GameState() {
    }

    /**
     * 
     */
    private Player challenger;

    /**
     * 
     */
    private List<Player> players;

    /**
     * 
     */
    private Turn turn;




    /**
     * @param player
     */
    public void setChallenger(Player player ) {
        // TODO implement here
    }

    /**
     * @param list
     */
    public void setSelectedGods(List<God> list ) {
        // TODO implement here
    }

    /**
     * 
     */
    private void removePlayer() {
        // TODO implement here
    }

    /**
     * @return
     */
    public Player nextPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Turn getTurn() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Turn setTurn() {
        // TODO implement here
        return null;
    }

}