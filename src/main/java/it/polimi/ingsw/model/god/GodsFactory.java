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

    private GameState gameState;

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
        if (createdGods.size() >= 3)
            throw new IllegalAccessException(); //TODO qua ovviamente va aggiunto il numero di giocatori della lobby non per forza tre
        God g = null;
        switch (name) {
            case APOLLO:
                g = new Apollo();
                break;
            case ARTEMIS:
                g = new Artemis();
                break;
            case ATHENA:
                g = new Athena();
                break;
            case ATLAS:
                g = new Atlas();
                break;
            case DEMETER:
                g = new Demeter();
                break;
            case HEPHAESTUS:
                g = new Hephaestus();
                break;
            case MINOTAUR:
                g = new Minotaur();
                break;
            case PAN:
                g = new Pan();
                break;
            case PROMETHEUS:
                g = new Prometheus();
                break;
        }
        if (g == null) throw new IllegalArgumentException();
        createdGods.add(g);
        return g;
    }
}