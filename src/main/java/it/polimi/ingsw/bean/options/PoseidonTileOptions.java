package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.MessageParser;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class PoseidonTileOptions extends TileOptions {
    public PoseidonTileOptions(String nickname, Collection<Tile.IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, String message) {
        super(nickname, tilesToChoose, boardClone, operation, message);
        alert = "Insert a tile of the list and a number within 1 and 3. " +
                "Mind that you can't build more a block higher then  dome level";
    }

    @Override
    public String isValid(String userInput) {
        String toCheck = userInput.replace(" ", "").replace("(", "").replace(")", "");
        Pattern checkTile = Pattern.compile("[01234],[01234]-[123]");
        if (checkTile.matcher(toCheck).matches()) {
            List args = ((List) MessageParser.parseMessage(this, userInput));
            Tile.IndexTile indexTile = (Tile.IndexTile) args.get(0);
            int admittedLevel = (int) args.get(1) + getBoardClone().getBuildingLevel(indexTile);
            if (tilesToChoose.contains(indexTile)
                    && admittedLevel <= 4)
                return null;
            else return alert;
        } else return alert;
    }
}
