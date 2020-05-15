package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.client.MessageParser;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;

import java.util.Collection;
import java.util.regex.Pattern;

public class PlaceWorkersOptions extends TileOptions {
    public PlaceWorkersOptions(String nickname, Collection<Tile.IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, String message) {
        super(nickname, tilesToChoose, boardClone, operation, message);
        alert = "Please insert two couples of numbers:\n" +
                ">> numbers in a specific couple has to be divided by a comma;\n" +
                ">> different couples has to be divided by a dash (-)";
    }

    @Override
    public String isValid(String userInput) {
        String toCheck = userInput.replace(" ", "").replace("(", "").replace(")", "");
        Pattern checkTile = Pattern.compile("[01234],[01234]-[01234],[01234]");
        if (checkTile.matcher(toCheck).matches()) {
            Tile.IndexTile[] tilesChosen = (Tile.IndexTile[]) MessageParser.parseDoubleIndex(userInput);
            if (!tilesChosen[0].equals(tilesChosen[1]) && tilesToChoose.contains(tilesChosen[0]) &&
                    tilesToChoose.contains(tilesChosen[1])) {
                return null;
            }
        }
        return alert;
    }

}
