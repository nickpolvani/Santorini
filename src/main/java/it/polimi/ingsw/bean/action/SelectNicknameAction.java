package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

public class SelectNicknameAction extends Action {

    public SelectNicknameAction(String nickname) {
        super(nickname);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SelectNicknameAction)) return false;
        return super.equals(obj);
    }
}
