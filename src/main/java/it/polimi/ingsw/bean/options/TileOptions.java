package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.MessageParser;
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

    protected final Collection<IndexTile> tilesToChoose;
    private final IslandBoard boardClone;
    /*
    This property is used to avoid printing another time the board after a SELECT_WORKER operation.
    In fact that operation does not change the state of the board, so it's useless printing the board another time.
     */
    private static boolean afterSelectWorker;

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
        if (!afterSelectWorker && !afterChooseOption) {
            view.printBoard(boardClone);
            if (currentOperation == Operation.SELECT_WORKER) {
                afterSelectWorker = true;
            }
        } else {
            if (afterSelectWorker) afterSelectWorker = false;
            if (afterChooseOption) afterChooseOption = false;
        }
        if (view.getNickname().equals(this.nickname)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(messageType).append(":\n");
            for (IndexTile i : tilesToChoose) {
                stringBuilder.append(i.toString()).append(" ");
            }
            stringBuilder.append(alert);
            view.showMessage(stringBuilder.toString());
        }
    }

    @Override
    public String isValid(String userInput) {
        String toCheck = userInput.replace(" ", "").replace("(", "").replace(")", "");
        Pattern checkTile = Pattern.compile("[01234],[01234]");
        if (checkTile.matcher(toCheck).matches()) {
            IndexTile indexTile = (IndexTile) MessageParser.parseMessage(this, userInput);
            if (tilesToChoose.contains(indexTile))
                return null;
            else return alert;
        } else return alert;
    }

}