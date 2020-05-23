package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

import java.io.Serializable;

public abstract class Options implements Serializable {

    protected final String message;
    protected Operation currentOperation;
    protected String alert;
    protected String nickname;


    protected Options(String nickname, String messageType, Operation operation) {
        this.message = messageType;
        this.currentOperation = operation;
        this.nickname = nickname;
    }


    public Operation getCurrentOperation() {
        return currentOperation;
    }

    public String getMessage() {
        return message;
    }

    public final void execute(View view) {
        if (view instanceof CLI) {
            cliExecute((CLI) view);
        } else {
            guiExecute((GUI) view);
        }
    }

    protected abstract void guiExecute(GUI view);


    protected abstract void cliExecute(CLI view);

    public abstract String isValid(String userInput);

    public String getNickname() {
        return nickname;
    }


}
