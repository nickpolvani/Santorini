package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.server.Lobby;

import java.util.Objects;

/**
 * This is an setup action used when a user has to choose
 * the size of the new lobby to which it will be added.
 */
public class LobbySizeAction extends Action {

    /**
     * The size of lobby, it can be 2 or 3.
     */
    private final int lobbySize;

    /**
     * Default constructor
     * @param lobbySize The size of the new lobby.
     * @param nickname The player's nickname who generated the action.
     * @see Lobby
     */
    public LobbySizeAction(int lobbySize, String nickname) {
        super(nickname);
        if (lobbySize != 2 && lobbySize != 3) throw new IllegalArgumentException();
        this.lobbySize = lobbySize;
    }

    public int getLobbySize() {
        return lobbySize;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof LobbySizeAction)) return false;
        return super.equals(obj) && this.lobbySize == ((LobbySizeAction) obj).getLobbySize();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lobbySize);
    }
}
