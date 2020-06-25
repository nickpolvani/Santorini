package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.controller.Operation;

/**
 * we use it in case of setting both lobby size and nickname of the user.
 */
public class SetupOptions extends Options {

    public SetupOptions(String nickname, String messageType, Operation operation) {
        super(nickname, messageType, operation);
    }


    @Override
    protected void guiExecute(GUI gui) {
        if (this.currentOperation == Operation.SELECT_LOBBY_SIZE) {
            gui.chooseLobbySize();
        }
        gui.showMessage(message);
    }

    @Override
    protected void cliExecute(CLI view) {
        view.showMessage(message);
    }

    @Override
    public String isValid(String userInput) {
        if (currentOperation == Operation.SELECT_NICKNAME) {
            if (userInput.isEmpty()) {
                return "You have given an empty nickname. Choose a valid nickname";
            }
            return null;
        } else if (currentOperation == Operation.SELECT_LOBBY_SIZE) {
            int size;
            try {
                size = Integer.parseInt(userInput);
            } catch (Exception e) {
                return "Insert number is not valid. Please try again!";
            }
            if (size != 3 && size != 2) {
                return "Insert number is not valid. Please try again!";
            }
            return null;
        }
        throw new IllegalArgumentException();
    }
}
