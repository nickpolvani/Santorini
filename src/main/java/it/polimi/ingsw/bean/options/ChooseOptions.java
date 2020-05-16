package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;

import java.util.Arrays;


/**
 * ChooseOptions are instantiated when the player has to make a decision during the turn,
 * due to the power of his chosen God.
 */
public class ChooseOptions extends Options {

    public ChooseOptions(String nickname, String message) {
        super(nickname, message, Operation.CHOOSE);
        alert = "Please insert [Yes/No] or [Y,N]";
    }

    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType + "\n" + alert);
        }
    }

    @Override
    public String isValid(String userInput) {
        String[] acceptedInput = {"yes", "no", "y", "n"};
        if (Arrays.stream(acceptedInput).anyMatch(x -> x.equals(userInput.toLowerCase()))) {
            return null;
        } else return alert;

    }
}
