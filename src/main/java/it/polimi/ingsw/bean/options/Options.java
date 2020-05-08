package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;

import java.io.Serializable;

/**
 * classes Options are used to notify the user of possible options to choose from.
 */

//TODO In the Actions we have the method run(), we need the equivalent in this classes, but we don't know how the View works yet
//The alternative is to do an explicit cast in the View class to get variables that are
// not in this class, but are in subclasses.
public abstract class Options implements Serializable {


    /**
     * the attribute player refers to the user that has to make an action
     * based on the options available, all the other players cannot make actions
     * they have to wait for their turn.
     * TODO This aspect has to be checked by the View
     */
    private final Player player;

    protected Operation currentOperation;

    protected final MessageType messageType;

    public Options(Player player, MessageType messageType) {
        this.player = player;
        this.messageType = messageType;
    }

    public Player getPlayer() {
        return player;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public abstract void execute();


    public enum MessageType {
        WIN,
        LOST,
        NOT_ALLOWED,
        MOVE("These are the Tiles where you can move"),
        BUILD("These are the Tiles where you can build"),
        PLACE_WORKERS("Choose two tiles where you want to place your workers"),
        SELECT_WORKER("Choose one of your workers"),
        CHOOSE,
        CHOOSE_GOD("Choose a God");

        private String message;

        MessageType(String message) {
            this.message = message;
        }

        MessageType() {
        }

        public String getMessage() {
            return message;
        }

        // pattern fluent interface: it allows you to set the message and to use at the same time the set object.
        public MessageType setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
