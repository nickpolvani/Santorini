package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

/**
 * classes PlayerOptions are used to notify the user of possible options to choose from.
 */

//TODO In the Actions we have the method run(), we need the equivalent in this classes, but we don't know how the View works yet
//The alternative is to do an explicit cast in the View class to get variables that are
// not in this class, but are in subclasses.
public abstract class PlayerOptions extends Options {
    /**
     * the attribute player refers to the user that has to make an action
     * based on the options available, all the other players cannot make actions
     * they have to wait for their turn.
     * TODO This aspect has to be checked by the View
     */
    private final Player player;

    public PlayerOptions(Player player, MessageType messageType, Operation operation) {
        super(messageType, operation);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
