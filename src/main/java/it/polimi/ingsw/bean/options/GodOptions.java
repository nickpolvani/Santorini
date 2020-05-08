package it.polimi.ingsw.bean.options;

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
    public void execute() {

    }
}
