package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.Player;

public class MessageOption extends Options {

    private final Enum messageType; // anEnum that the user will see from the View

    public MessageOption(Player player, Enum messageType) {
        super(player);
        this.messageType = messageType;
    }

    public Enum getMessageType() {
        return messageType;
    }

    public enum Enum {
        WIN, LOST, NOTALLOWED;

        private String message;

        public String getMessage() {
            return message;
        }

        // pattern fluent interface: it allows you to set the message and to use at the same time the set object.
        public Enum setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
