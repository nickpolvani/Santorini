package it.polimi.ingsw.Bean.Choice;

import it.polimi.ingsw.Model.*;

public class GodChoice {
    Player player;
    NameGod chosenGod;

    public GodChoice(Player player, NameGod chosenGod) {
        this.player = player;
        this.chosenGod = chosenGod;
    }

    public Player getPlayer() {
        return player;
    }

    public NameGod getChosenGod() {
        return chosenGod;
    }
}
