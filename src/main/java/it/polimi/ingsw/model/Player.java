package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.AlreadySetException;
import it.polimi.ingsw.model.god.God;

/**
 * @author Francesco Puoti
 */
public class Player {

    /**
     * Name Inserted by the player during the game's initialization
     */
    private final String nickname;
    /**
     * The reference to the player's workers
     */
    private Worker[] worker;
    /**
     * it's provides the reference of the god selected by the player, who will use it for all the game
     */
    private God god;
    /**
     * if player loses the game, this boolean will be set true
     */
    private Boolean hasLost;

    /**
     * Default constructor
     * When the game is initialized, every client has to provide his nickname. In this way, when we create the player's instance,
     * we set also his nickname. Therefore, the setter of nickname is unnecessary and useless.
     */
    public Player(String nickname) {

        this.nickname = nickname;
        this.hasLost = false;
    }

    /**
     * @return player's nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * @return this.worker
     */
    public Worker[] getWorker() {
        return this.worker;
    }

    /**
     * @param color : when I set players's workers' team, I need to set the color of the team
     */
    public void setWorker(Color color) throws AlreadySetException {

        if (this.worker != null) throw new AlreadySetException("Team already set");
        this.worker = new Worker[2];
        for (int i = 0; i < worker.length; i++) {
            worker[i] = new Worker();
            worker[i].setColor(color);
        }
    }

    /**
     * @return this.god
     */
    public God getGod() {
        return this.god;
    }

    /**
     * @param selectedGod : this parameter comes form the controller, that claims the player's choice of the god
     */
    public void setGod(God selectedGod) {
        this.god = selectedGod;
    }

    /**
     * @return hasLost
     */
    public Boolean getHasLost() {
        return this.hasLost;
    }

    /**
     * @param hasLost : this setter will be called if player will lose the game
     */
    public void setHasLost(Boolean hasLost) {
        this.hasLost = hasLost;
    }

}