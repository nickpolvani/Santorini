package it.polimi.ingsw.Model;

import it.polimi.ingsw.Controller.God.God;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class Player {

    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private Worker[] worker;
    /**
     *
     */
    private NameGod god;
    /**
     *
     */
    private Boolean hasLost;

    /**
     * Default constructor
     */
    public Player() {
    }

    /**
     * @return
     */
    public String getNickname() {
        // TODO implement here
        return "";
    }

    /**
     * @param s
     */
    public void setNickname(String s) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Worker getWorker() {
        // TODO implement here
        return null;
    }

    /**
     * @param w
     */
    public void setWorker(Worker w) {
        // TODO implement here
    }

    /**
     * @return
     */
    public God getGod() {
        // TODO implement here
        return null;
    }

    /**
     * @param g
     */
    public void setGod(God g) {
        // TODO implement here
    }

}