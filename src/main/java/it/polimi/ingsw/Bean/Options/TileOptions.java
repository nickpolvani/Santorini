package it.polimi.ingsw.Bean.Options;

import it.polimi.ingsw.Model.*;

public class TileOptions {
    Player player;
    Tile[] tilesToChoose;
    IslandBoard board;
    String message;

    public TileOptions(Player player, Tile[] tilesToChoose, IslandBoard board, String message) {
        this.player = player;
        this.tilesToChoose = tilesToChoose;
        this.board = board;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public Tile[] getTilesToChoose() {
        return tilesToChoose;
    }

    public IslandBoard getBoard() {
        return board;
    }

    public String getMessage() {
        return message;
    }



}
