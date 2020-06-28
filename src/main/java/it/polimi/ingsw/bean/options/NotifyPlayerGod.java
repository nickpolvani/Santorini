package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.god.GodDescription;

/**
 * Option used to tell users their chosen god
 */
public class NotifyPlayerGod extends MessageOption {
    private final GodDescription playerGod;

    public NotifyPlayerGod(String nickname, String messageType, GodDescription god) {
        super(nickname, messageType);
        playerGod = god;
    }

    @Override
    protected void guiExecute(GUI gui) {
        if (this.nickname.equals(gui.getNickname())) {
            gui.setPlayerGod(playerGod);
        }
    }

}
