package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.model.god.GodDescription;

public class NotifyPlayerGod extends MessageOption {
    private final GodDescription playerGod;

    public NotifyPlayerGod(String nickname, String messageType, GodDescription god) {
        super(nickname, messageType);
        playerGod = god;
    }

    @Override
    protected void guiExecute(GUI view) {
        if (this.nickname.equals(view.getNickname())) {
            view.setPlayerGod(playerGod);
        }
    }

}
