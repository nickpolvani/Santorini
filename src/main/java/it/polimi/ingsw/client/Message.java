package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

public class Message {

    public static Object parseMessage(Options currentOption, String message) {
        Object object;
        switch (currentOption.getCurrentOperation()) {
            case SELECT_NICKNAME:
                object = message;
                break;
            case SELECT_LOBBY_SIZE:
                object = Integer.parseInt(message);
                break;
            case MOVE:
            case BUILD:
            case SELECT_WORKER:
                object = parseSingleIndex(message);
                break;
            case CHOOSE:
                object = parseBoolean(message);
                break;
            case CHOOSE_GOD:
                object = parseGodDescription(message);
                break;
            case PLACE_WORKERS:
                object = parseDoubleIndex(message);
                break;
            case MESSAGE_NO_REPLY:
                object = null;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return object;
    }

    public static Object parseDoubleIndex(String message) {
        String[] split = message.split("-");
        Tile.IndexTile[] index = new Tile.IndexTile[2];
        index[0] = (parseSingleIndex(split[0]));
        index[1] = (parseSingleIndex(split[1]));
        return index;
    }

    private static GodDescription parseGodDescription(String message) {
        return GodDescription.parse(message);
    }

    private static Boolean parseBoolean(String message) {
        return message.toLowerCase().equals("yes") || message.toLowerCase().equals("y");
    }

    private static Tile.IndexTile parseSingleIndex(String message) {
        String[] numbersOfIndex = message.split(",");
        return new Tile.IndexTile(Integer.parseInt(numbersOfIndex[0]), Integer.parseInt(numbersOfIndex[1]));
    }
}
