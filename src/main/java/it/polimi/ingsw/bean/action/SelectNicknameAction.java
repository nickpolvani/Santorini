package it.polimi.ingsw.bean.action;

/**
 * action used before the game starts to let users select their nickname
 */
public class SelectNicknameAction extends Action {

    public SelectNicknameAction(String nickname) {
        super(nickname);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SelectNicknameAction)) return false;
        return super.equals(obj);
    }
}
