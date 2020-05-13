package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

/**
 * we use it in case of setting both lobby size and nickname of the user.
 */
public class SetupOptions extends Options {


    public SetupOptions(String nickname, MessageType messageType, Operation operation) {
        super(nickname, messageType, operation);
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void execute(View view) {
        view.showMessage(messageType.getMessage());
    }

    @Override
    public String isValid(String userInput) {
        if (currentOperation == Operation.SELECT_NICKNAME)
            return null;
        else if (currentOperation == Operation.SELECT_LOBBY_SIZE) {
            int size = Integer.parseInt(userInput);
            if (size != 3 && size != 2) {
                return "Insert number is not valid. Please try again!";
            }
            return null;
        }
        throw new IllegalArgumentException();
    }
}
