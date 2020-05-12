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
    }

    public List<GodDescription> getGodsToChoose() {
        return godsToChoose;
    }

    @Override
    public void execute(View view) {
        StringBuilder mess = new StringBuilder(this.messageType.getMessage());
        for (GodDescription g : godsToChoose) {
            mess.append(g.toString() + "\n");
        }
        view.showMessage(mess.toString());
    }

    @Override
    public String isValid(String attribute) {
        return null;
    }
}
