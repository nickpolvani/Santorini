package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * class used when the player has to choose between a set of tiles, for example to select a Tile to build, move
 * or select the worker for his turn. Contains also a copy of the board to notify the user that the game has
 * changed.
 */
public class TilePlayerOptions extends PlayerOptions {

    private final Collection<IndexTile> tilesToChoose;
    private final IslandBoard boardClone;

    public TilePlayerOptions(Player player, Collection<IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, MessageType message) {
        super(player, message, operation);
        this.tilesToChoose = tilesToChoose;
        this.boardClone = boardClone;
        alert = "Please insert only one couple of numbers split by a comma." +
                "Obviously, numbers has to be within 0 and 4";
    }

    public Collection<IndexTile> getTilesToChoose() {
        return tilesToChoose;
    }

    public IslandBoard getBoardClone() {
        return boardClone;
    }

    @Override
    public void execute(View view) {
        view.printBoard(boardClone);
        if (view.getNickname().equals(this.getPlayer().getNickname())) {
            view.showMessage(messageType.getMessage() + alert);
        }
    }

    @Override
    public String isValid(String userInput) {
        Pattern checkTile = Pattern.compile("[01234],[01234]");
        if (checkTile.matcher(userInput).matches()) {
            return null;
        } else return alert;
    }

}