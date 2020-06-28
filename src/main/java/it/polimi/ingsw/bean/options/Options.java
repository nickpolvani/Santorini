package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

import java.io.Serializable;

/**
 * Options are sent by the server to every client, the user identified by the variable nickname
 * is the one that has to perform some operation indicated by the Options object. The latter contains also the valid
 * operations the user can perform.
 */
public abstract class Options implements Serializable {

    protected final String message;
    protected Operation currentOperation;
    /**
     * message used mostly in CLI to tell user valid input format
     */
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

    /**
     * calls methods of GUI or CLI to update the user interface
     *
     * @param view the client view
     */
    public final void execute(View view) {
        if (view instanceof CLI) {
            cliExecute((CLI) view);
        } else {
            guiExecute((GUI) view);
        }
    }

    /**
     * calls methods of gui to update the user interface
     *
     * @param gui instance of the graphical user interface controller
     */
    protected abstract void guiExecute(GUI gui);

    /**
     * calls methods of cli to notify user about game changes and actions to perform
     *
     * @param cli instance of the command-line user interface controller
     */
    protected abstract void cliExecute(CLI cli);

    /**
     * @param userInput string given by user in CLI or generated automatically interacting with GUI
     * @return a string containing the error message if userInput is not valid, null otherwise
     */
    public abstract String isValid(String userInput);

    public String getNickname() {
        return nickname;
    }


}
