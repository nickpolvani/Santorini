package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.List;

/**
 * These type of Options are sent to the user in setup, when they have to choose between a set of
 * Gods. Since the game has not started yet, There is no need to have a board in these type of options
 */
public class GodOptions extends Options {

    private final List<GodDescription> godsToChoose;

    public GodOptions(String nickname, List<GodDescription> godsToChoose, MessageType message) {
        super(nickname, message, Operation.CHOOSE_GOD);
        this.godsToChoose = godsToChoose;
        alert = "Please insert one of the list:";
    }


    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType.getMessage() + godsList() + alert);
        }
    }

    @Override
    public String isValid(String userInput) {


        if (godsToChoose.stream().
                anyMatch(x -> x.getName().toLowerCase().equals(userInput.toLowerCase()))) {
            return null;
        }

        return "Not Valid Input: " + alert;
    }

    private String godsList() {
        StringBuilder list = new StringBuilder(this.messageType.getMessage());
        for (GodDescription g : godsToChoose) {
            list.append(g.toString() + "\n");
        }
        return list.toString();
    }
}
