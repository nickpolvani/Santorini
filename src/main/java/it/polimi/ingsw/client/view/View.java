package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.IslandBoard;

public abstract class View {

    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname != null && !nickname.isEmpty()) throw new IllegalStateException();
        this.nickname = nickname;
    }

    public abstract void showMessage(String message);

    public abstract void updateBoard(IslandBoard board);
}
