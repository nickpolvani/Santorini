package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;

/**
 * class used when the player has to choose between a set of tiles, for example to select a Tile to build, move
 * or select the worker for his turn. Contains also a copy of the board to notify the user that the game has
 * changed.
 */
public class TileOptions extends Options {

    private final Collection<IndexTile> tilesToChoose;
    private final IslandBoard boardClone;

    public TileOptions(Player player, Collection<IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, MessageType message) {
        super(player, message);
        this.tilesToChoose = tilesToChoose;
        this.currentOperation = operation;
        this.boardClone = boardClone;
    }

    public Collection<IndexTile> getTilesToChoose() {
        return tilesToChoose;
    }

    public IslandBoard getBoardClone() {
        return boardClone;
    }

    @Override
    public void execute() {

    }
}