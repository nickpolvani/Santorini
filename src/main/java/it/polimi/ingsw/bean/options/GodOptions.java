package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.List;

/**
 * These type of Options are sent to the user in setup, when they have to choose between a set of
 * Gods. Since the game has not started yet, There is no need to have a board in these type of options
 */
public class GodOptions extends Options {

    private final List<GodDescription> godsToChoose;

    public GodOptions(Player player, List<GodDescription> godsToChoose, MessageType message) {
        super(player, message);
        this.godsToChoose = godsToChoose;
        this.currentOperation = Operation.CHOOSE_GOD;
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
    public boolean isValid(Action currentAction) {
        return false;
    }
}
