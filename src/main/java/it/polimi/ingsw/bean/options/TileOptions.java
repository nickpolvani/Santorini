package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

/**
 * class used when the player has to choose between a set of tiles, for example to select a Tile to build, move
 * or select the worker for his turn. Contains also a copy of the board to notify the user that the game has
 * changed.
 */
public class TileOptions extends Options {

    private final Tile[] tilesToChoose;
    private final IslandBoard boardClone;
    private final String message;

    public TileOptions(Player player, Tile[] tilesToChoose, IslandBoard boardClone, String message) {
        super(player);
        this.tilesToChoose = tilesToChoose;
        this.boardClone = boardClone;
        this.message = message;
    }


    public Tile[] getTilesToChoose() {
        return tilesToChoose;
    }

    public IslandBoard getBoardClone() {
        return boardClone;
    }

    public String getMessage() {
        return message;
    }
}