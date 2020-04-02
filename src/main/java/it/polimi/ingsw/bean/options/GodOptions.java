package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.god.GodNameAndDescription;

/**
 * These type of Options are sent to the user in setup, when they have to choose between a set of
 * Gods. Since the game has not started yet, There is no need to have a board in these type of options
 */
public class GodOptions extends Options {

    private final GodNameAndDescription[] godsToChoose;
    private final String message;

    public GodOptions(IslandBoard board, Player player, GodNameAndDescription[] godsToChoose, String message) {
        super(player);
        this.godsToChoose = godsToChoose;
        this.message = message;
    }


    public GodNameAndDescription[] getGodsToChoose() {
        return godsToChoose;
    }

    public String getMessage() {
        return message;
    }
}
