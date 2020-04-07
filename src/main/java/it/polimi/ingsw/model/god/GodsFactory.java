package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.GameState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class GodsFactory {

    /**
     * TODO forse qua sarebbe meglio un array, la dim la prendiamo del parametro della lobby dove salveremo il numero di giocatori
     */
    private final List<God> createdGods = new ArrayList<>();

    private final GameState gameState;

    public GodsFactory(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @param name
     * @return
     */
    public God getGod(GodNameAndDescription name) throws IllegalAccessException {
        for (God g : createdGods) {
            if (g.getNameAndDescription().equals(name)) {
                return g;
            }
        }

        if (createdGods.size() == gameState.getPlayers().size())
            throw new IllegalAccessException();
        God g = null;
        switch (name) {
            case APOLLO:
                g = new Apollo(gameState);
                break;
            case ARTEMIS:
                g = new Artemis(gameState);
                break;
            case ATHENA:
                g = new Athena(gameState);
                break;
            case ATLAS:
                g = new Atlas(gameState);
                break;
            case DEMETER:
                g = new Demeter(gameState);
                break;
            case HEPHAESTUS:
                g = new Hephaestus(gameState);
                break;
            case MINOTAUR:
                g = new Minotaur(gameState);
                break;
            case PAN:
                g = new Pan(gameState);
                break;
            case PROMETHEUS:
                g = new Prometheus(gameState);
                break;
            default:
                throw new IllegalArgumentException();
        }
        createdGods.add(g);
        return g;
    }
}