package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

public class TileOptions {
    private final Player player;
    private final Tile[] tilesToChoose;
    private final IslandBoard board;
    private final String message;

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