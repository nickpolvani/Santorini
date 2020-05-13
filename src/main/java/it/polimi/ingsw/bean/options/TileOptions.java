package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.Message;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile.IndexTile;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * class used when the player has to choose between a set of tiles, for example to select a Tile to build, move
 * or select the worker for his turn. Contains also a copy of the board to notify the user that the game has
 * changed.
 */
public class TileOptions extends Options {

    private final Collection<IndexTile> tilesToChoose;
    private final IslandBoard boardClone;

    public TileOptions(String nickname, Collection<IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, String message) {
        super(nickname, message, operation);
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
        if (view.getNickname().equals(this.nickname)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(messageType).append(" ").append(alert).append(":\n");
            for (IndexTile i : tilesToChoose) {
                stringBuilder.append(i.toString() + " ");
            }
            view.showMessage(stringBuilder.toString());
        }
    }

    @Override
    public String isValid(String userInput) {
        Pattern checkTile = Pattern.compile("[01234],[01234]");
        if (checkTile.matcher(userInput).matches()) {
            IndexTile indexTile = (IndexTile) Message.parseMessage(this, userInput);
            if (tilesToChoose.contains(indexTile))
                return null;
            else return alert;
        } else return alert;
    }

}