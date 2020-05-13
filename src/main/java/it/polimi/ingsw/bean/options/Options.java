package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

import java.io.Serializable;

public abstract class Options implements Serializable {

    protected final MessageType messageType;
    protected Operation currentOperation;
    protected String alert;

    protected Options(MessageType messageType, Operation operation) {
        this.messageType = messageType;
        this.currentOperation = operation;
    }

    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public abstract void execute(View view);

    public abstract String isValid(String userInput);

    public enum MessageType {
        WIN,
        LOST,
        NOT_ALLOWED,
        MOVE("These are the Tiles where you can move\n"),
        BUILD("These are the Tiles where you can build\n"),
        PLACE_WORKERS("Choose two tiles where you want to place your workers\n"),
        SELECT_WORKER("Choose one of your workers\n"),
        CHOOSE,
        CHOOSE_GOD("Choose a God\n"),
        CHOOSE_NAME("Choose your nickname\n"),
        CHOOSE_LOBBY_SIZE("How many people do you want to play with?\n"),
        NICKNAME_ALREADY_SET("We are sorry but your nickname has been already chosen. Please insert another one.\n"),
        NICKNAME_APPROVED("Well done, your nickname is valid!\n");

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
