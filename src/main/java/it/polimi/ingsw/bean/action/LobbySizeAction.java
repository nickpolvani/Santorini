package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

public class LobbySizeAction extends Action {

    private final int lobbySize;

    public LobbySizeAction(String nickname, int lobbySize) {
        super(nickname);
        this.lobbySize = lobbySize;
    }

    public int getLobbySize() {
        return lobbySize;
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return null;
    }
}
