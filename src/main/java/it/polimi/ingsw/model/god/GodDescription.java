package it.polimi.ingsw.model.god;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum GodDescription {
    APOLLO("Apollo", "Your Worker may  move into an opponent Worker’s space by forcing their " +
            "Worker to the space just vacated."),
    ARTEMIS("Artemis", "Your Worker may move one additional time, but not back to its initial space."),
    ATHENA("Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."),
    ATLAS("Atlas", "Your Worker may build a dome at any level."),
    DEMETER("Demeter", "Your Worker may build one additional time, but not on the same space."),
    PAN("Pan", "You also win if your worker moves down two or more levels"),
    MINOTAUR("Minotaur", "Your Worker may  move into an opponent Worker’s space, if their Worker can " +
            "be forced one space straight backwards to an unoccupied space at any level."),
    PROMETHEUS("Prometheus", "If your Worker does not move up, it may build both before and after moving."),
    HEPHAESTUS("Hephaestus", "Your Worker may build one additional block (not dome) on top of your first block."),
    //Advanced GOD
    CHARON("Charon", "Before your Worker moves, you may force a neighboring opponent Worker to " +
            "the space directly on the other side of your Worker, if that space is unoccupied."),
    HESTIA("Hestia", "Your Worker may build one additional time, but this cannot be on a perimeter space."),
    MEDUSA("Medusa", "If possible, your Workers build in lower neighboring spaces that are " +
            "occupied by opponent Workers, removing the opponent Workers from the game."),
    POSEIDON("Poseidon", "If your unmoved Worker is on the ground level, it may build up to three times."),
    /* Implement Ares if poseidon is too difficult
    ARES("Ares", "You may remove an unoccupied block (not dome) neighboring your unmoved Worker."),*/
    ZEUS("Zeus", "Your Worker may build a block under itself. " +
            "If you decide to build under your worker, you won't be able to perform a normal build operation");

    private final String name;
    private final String descriptionOfPower;


    GodDescription(String name, String description) {
        this.name = name;
        this.descriptionOfPower = description;
    }

    public String getName() {
        return name;
    }

    public String getDescriptionOfPower() {
        return descriptionOfPower;
    }


    @Override
    public String toString() {
        return name + " " + descriptionOfPower;
    }

    public static GodDescription parse(String godName) throws IllegalArgumentException {
        if (godName.toUpperCase().equals(APOLLO.getName().toUpperCase())) {
            return APOLLO;
        } else if (godName.toUpperCase().equals(ARTEMIS.getName().toUpperCase())) {
            return ARTEMIS;
        } else if (godName.toUpperCase().equals(ATHENA.getName().toUpperCase())) {
            return ATHENA;
        } else if (godName.toUpperCase().equals(ATLAS.getName().toUpperCase())) {
            return ATLAS;
        } else if (godName.toUpperCase().equals(DEMETER.getName().toUpperCase())) {
            return DEMETER;
        } else if (godName.toUpperCase().equals(PAN.getName().toUpperCase())) {
            return PAN;
        } else if (godName.toUpperCase().equals(MINOTAUR.getName().toUpperCase())) {
            return MINOTAUR;
        } else if (godName.toUpperCase().equals(PROMETHEUS.getName().toUpperCase())) {
            return PROMETHEUS;
        } else if (godName.toUpperCase().equals(HEPHAESTUS.getName().toUpperCase())) {
            return HEPHAESTUS;
        } else if (godName.toUpperCase().equals(CHARON.getName().toUpperCase())) {
            return CHARON;
        } else if (godName.toUpperCase().equals(HESTIA.getName().toUpperCase())) {
            return HESTIA;
        } else if (godName.toUpperCase().equals(MEDUSA.getName().toUpperCase())) {
            return MEDUSA;
        } else if (godName.toUpperCase().equals(POSEIDON.getName().toUpperCase())) {
            return POSEIDON;
        } else if (godName.toUpperCase().equals(ZEUS.getName().toUpperCase())) {
            return ZEUS;
        } else {
            throw new IllegalArgumentException();
        }
    }
}