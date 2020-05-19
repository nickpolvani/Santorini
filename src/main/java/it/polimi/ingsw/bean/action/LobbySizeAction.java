package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

public class LobbySizeAction extends Action {

    private final int lobbySize;

    public LobbySizeAction(int lobbySize, String nickname) {
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LobbySizeAction)) return false;
        return super.equals(obj) && this.lobbySize == ((LobbySizeAction) obj).getLobbySize();
    }
}
