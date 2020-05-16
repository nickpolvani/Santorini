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
    private final String alertForChallenger = " Please insert gods of the list split by a comma.";

    public GodOptions(String nickname, List<GodDescription> godsToChoose, String message) {
        super(nickname, message, Operation.CHOOSE_GOD);
        this.godsToChoose = godsToChoose;
        alert = "Please insert one of the list.";
    }

    public int getGodsListSize() {
        return godsToChoose.size();
    }

    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.nickname)) {
            view.showMessage(messageType + "\n" + godsList() + (godsToChoose.size() == 9 ? alertForChallenger : alert));
        }
    }

    @Override
    public String isValid(String userInput) {
        if (godsToChoose.size() == 9) {
            String[] toCheck = userInput.replace(" ", "").toLowerCase().split(",");
            for (String s : toCheck) {
                if (godsToChoose.stream().noneMatch(x -> x.getName().toLowerCase().equals(s))) {
                    return "Not valid input: " + alertForChallenger;
                }
            }
            return null;
        } else {
            if (godsToChoose.stream().
                    anyMatch(x -> x.getName().toLowerCase().equals(userInput.toLowerCase()))) {
                /*Mind that if the player tries to insert more than one gods, the inserted string does not match
                with any of the gods in the list. Thus, the correct number of the god, which inserted by a player
                who is not the challenger, is checked too.*/
                return null;
            } else {
                return "Not Valid Input: " + alert;
            }
        }
    }

    private String godsList() {
        StringBuilder list = new StringBuilder();
        for (GodDescription g : godsToChoose) {
            list.append(">").append(g.getName()).append(":\n").append("    ").append(g.getDescriptionOfPower()).append("\n");
        }
        return list.toString();
    }
}
