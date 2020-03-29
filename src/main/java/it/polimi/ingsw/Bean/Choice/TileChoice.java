package it.polimi.ingsw.Bean.Choice;

import it.polimi.ingsw.Model.*;

public class TileChoice {
    Player player;
    Tile chosenTile;

    public TileChoice(Player player, Tile chosenTile) {
        this.player = player;
        this.chosenTile = chosenTile;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile getChosenTile() {
        return chosenTile;
    }
}
