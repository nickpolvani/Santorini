package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

import java.util.ArrayList;
import java.util.List;

public class MessageParser {

    private MessageParser() {
    }

    public static Object parseMessage(Options currentOption, String message) {
        Object object;
        switch (currentOption.getCurrentOperation()) {
            case SELECT_NICKNAME:
                object = null;
                break;
            case SELECT_LOBBY_SIZE:
                object = Integer.parseInt(message);
                break;
            case MOVE:
            case BUILD:
            case REMOVE_BLOCK:
            case SELECT_WORKER:
            case SELECT_OPPONENTS_WORKER:
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
            case POSEIDON_BUILD:
                object = parsePoseidonInput(message);
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


    private static List<GodDescription> parseGodDescription(String message) {
        List<GodDescription> gods = new ArrayList<>();
        String[] godsArray = message.replace(" ", "").split(",");
        for (String s : godsArray) {
            gods.add(GodDescription.parse(s));
        }
        return gods;
    }

    private static Boolean parseBoolean(String message) {
        return message.equalsIgnoreCase("yes") || message.equalsIgnoreCase("y");
    }

    private static Tile.IndexTile parseSingleIndex(String message) {
        message = message.replace(" ", "").replace("(", "").replace(")", "");
        String[] numbersOfIndex = message.split(",");
        return new Tile.IndexTile(Integer.parseInt(numbersOfIndex[0]), Integer.parseInt(numbersOfIndex[1]));
    }

    private static List<Object> parsePoseidonInput(String message) {
        List<Object> args = new ArrayList<>();
        String[] split = message.replace(" ", "").split("-");
        args.add(parseSingleIndex(split[0]));
        args.add(Integer.parseInt(split[1]));
        return args;
    }
}
