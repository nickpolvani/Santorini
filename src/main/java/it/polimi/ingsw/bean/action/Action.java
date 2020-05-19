package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

import java.io.Serializable;

public abstract class Action implements Serializable {
    private final String nickname;

    public Action(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    /**
     * @param operation is the current operation in Turn
     * @return true if the operation is compatible with the action
     */
    public abstract Boolean isCompatible(Operation operation);

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Action)) return false;
        return nickname.equals(((Action) obj).getNickname());
    }
}
