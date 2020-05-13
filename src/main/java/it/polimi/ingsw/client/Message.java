package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.ArrayList;

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
            case SEND_MESSAGE:
                object = null;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return object;
    }

    private static Object parseDoubleIndex(String message) {
        String[] split = message.split("-");
        ArrayList<Tile.IndexTile> index = new ArrayList<>();
        index.add(parseSingleIndex(split[0]));
        index.add(parseSingleIndex(split[1]));
        return index;
    }

    private static GodDescription parseGodDescription(String message) {
        return GodDescription.parse(message);
    }

    private static Boolean parseBoolean(String message) {
        return Boolean.parseBoolean(message);
    }

    private static Tile.IndexTile parseSingleIndex(String message) {
        String[] numbersOfIndex = message.split(",");
        return new Tile.IndexTile(Integer.parseInt(numbersOfIndex[0]), Integer.parseInt(numbersOfIndex[1]));
    }
}