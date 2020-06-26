package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.Player;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * GodsFactory implement a singleton pattern for each God in game.
 * This class is the only one that can instantiate gods.
 *
 * @see God
 * @see GameState
 */
public class GodsFactory {

    private final List<God> createdGods = new ArrayList<>();

    private final GameState gameState;

    public GodsFactory(GameState gameState) {
        this.gameState = gameState;
        Logger.getLogger("Server").debug("GodFactory created");
    }

    /**
     * The god we want to implements is given by this method. In this way, we ensure there will not
     * be any duplicate instance or reference and that only the player who has the god for the man
     * is allow to get the instance of the god.
     *
     * @param name   name of the god claimed
     * @param player player who claim the god
     * @return God's instance
     */
    public God getGod(GodDescription name, Player player) {
        for (God g : createdGods) {
            if (g.getGodDescription().equals(name)) {
                return g;
            }
        }

        if (createdGods.size() == gameState.getPlayers().size())
            throw new RuntimeException();
        God g;
        switch (name) {
            case APOLLO:
                g = new Apollo(gameState, player);
                break;
            case ARES:
                g = new Ares(gameState, player);
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
            case CHARON:
                g = new Charon(gameState, player);
                break;
            case DEMETER:
                g = new Demeter(gameState, player);
                break;
            case HEPHAESTUS:
                g = new Hephaestus(gameState, player);
                break;
            case HESTIA:
                g = new Hestia(gameState, player);
                break;
            case MEDUSA:
                g = new Medusa(gameState, player);
                break;
            case MINOTAUR:
                g = new Minotaur(gameState, player);
                break;
            case PAN:
                g = new Pan(gameState, player);
                break;
            case POSEIDON:
                g = new Poseidon(gameState, player);
                break;
            case PROMETHEUS:
                g = new Prometheus(gameState, player);
                break;
            case ZEUS:
                g = new Zeus(gameState, player);
                break;
            default:
                throw new IllegalArgumentException();
        }
        createdGods.add(g);
        return g;
    }
}