package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

import java.io.Serializable;

public abstract class Options implements Serializable {

    protected final String messageType;
    protected Operation currentOperation;
    protected String alert;
    protected String nickname;


    protected Options(String nickname, String messageType, Operation operation) {
        this.messageType = messageType;
        this.currentOperation = operation;
        this.nickname = nickname;
    }


    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public String getMessageType() {
        return messageType;
    }

    public abstract void execute(View view);

    public abstract String isValid(String userInput);

    public String getNickname() {
        return nickname;
    }


}
