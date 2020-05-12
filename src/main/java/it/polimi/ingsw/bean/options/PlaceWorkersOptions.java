package it.polimi.ingsw.bean.options;

import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Tile;

import java.util.Collection;
import java.util.regex.Pattern;

public class PlaceWorkersOptions extends TilePlayerOptions {
    public PlaceWorkersOptions(Player player, Collection<Tile.IndexTile> tilesToChoose, IslandBoard boardClone, Operation operation, MessageType message) {
        super(player, tilesToChoose, boardClone, operation, message);
        alert = "Please insert two couples of numbers:\n" +
                ">> numbers in a specific couple has to be divided by a comma;\n" +
                ">> different couples has to be divided by a dash (-)";
    }

    @Override
    public String isValid(String userInput) {
        String toCheck = userInput.replace(" ", "");
        Pattern checkTile = Pattern.compile("[01234],[01234]-[01234],[01234]");
        if (checkTile.matcher(toCheck).matches()) {
            return null;
        } else return alert;
    }

}
