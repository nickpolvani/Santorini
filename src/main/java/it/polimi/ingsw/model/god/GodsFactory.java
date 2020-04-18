package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public class GodsFactory {

    private final List<God> createdGods = new ArrayList<>();

    private final GameState gameState;

    public GodsFactory(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * @param name   name of the god claimed
     * @param player player who claim the god
     * @return God's instance
     */
    public God getGod(GodNameAndDescription name, Player player) {
        for (God g : createdGods) {
            if (g.getNameAndDescription().equals(name)) {
                return g;
            }
        }

        if (createdGods.size() == gameState.getPlayers().size())
            throw new RuntimeException();
        God g = null;
        switch (name) {
            case APOLLO:
                g = new Apollo(gameState, player);
                break;
            case ARTEMIS:
                g = new Artemis(gameState, player);
                break;
            case ATHENA:
                g = new Athena(gameState, player);
                break;
            case ATLAS:
                g = new Atlas(gameState, player);
                break;
            case DEMETER:
                g = new Demeter(gameState, player);
                break;
            case HEPHAESTUS:
                g = new Hephaestus(gameState, player);
                break;
            case MINOTAUR:
                g = new Minotaur(gameState, player);
                break;
            case PAN:
                g = new Pan(gameState, player);
                break;
            case PROMETHEUS:
                g = new Prometheus(gameState, player);
                break;
            default:
                throw new IllegalArgumentException();
        }
        createdGods.add(g);
        return g;
    }
}