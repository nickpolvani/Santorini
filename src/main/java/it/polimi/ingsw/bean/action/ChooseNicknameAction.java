package it.polimi.ingsw.bean.action;

import it.polimi.ingsw.controller.Operation;

public class ChooseNicknameAction extends Action {

    public ChooseNicknameAction(String nickname) {
        super(nickname);
    }

    @Override
    public Boolean isCompatible(Operation operation) {
        return null;
    }
}
