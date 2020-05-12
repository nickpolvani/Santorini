package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.List;

/**
 * These type of PlayerOptions are sent to the user in setup, when they have to choose between a set of
 * Gods. Since the game has not started yet, There is no need to have a board in these type of options
 */
public class GodPlayerOptions extends PlayerOptions {

    private final List<GodDescription> godsToChoose;

    public GodPlayerOptions(Player player, List<GodDescription> godsToChoose, MessageType message) {
        super(player, message, Operation.CHOOSE_GOD);
        this.godsToChoose = godsToChoose;
        alert = "Please insert one of the list:";
    }

    public List<GodDescription> getGodsToChoose() {
        return godsToChoose;
    }

    @Override
    public void execute(View view) {
        if (view.getNickname().equals(this.getPlayer().getNickname())) {
            view.showMessage(messageType.getMessage() + godsList() + alert);
        }
    }

    @Override
    public String isValid(String userInput) {
        if (godsList().toLowerCase().contains(userInput.toLowerCase())) {
            return null;
        } else return alert;
    }

    private String godsList() {
        StringBuilder list = new StringBuilder(this.messageType.getMessage());
        for (GodDescription g : godsToChoose) {
            list.append(g.toString() + "\n");
        }
        return list.toString();
    }
}
