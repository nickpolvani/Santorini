package it.polimi.ingsw.model.god;

/**
 * Contains name and description of god classes
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
    ARES("Ares", "You may remove an unoccupied block (not dome) neighboring your unmoved Worker."),
    CHARON("Charon", "Before your Worker moves, you may force a neighboring opponent Worker to " +
            "the space directly on the other side of your Worker, if that space is unoccupied."),
    HESTIA("Hestia", "Your Worker may build one additional time, but this cannot be on a perimeter space."),
    MEDUSA("Medusa", "If possible, your Workers build in lower neighboring spaces that are " +
            "occupied by opponent Workers, removing the opponent Workers from the game."),
    POSEIDON("Poseidon", "If your unmoved Worker is on the ground level, " +
            "it may build up to three times."),
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

    public static GodDescription parse(String godName) {
        if (godName.equalsIgnoreCase(APOLLO.getName())) {
            return APOLLO;
        } else if (godName.equalsIgnoreCase(ARTEMIS.getName())) {
            return ARTEMIS;
        } else if (godName.equalsIgnoreCase(ATHENA.getName())) {
            return ATHENA;
        } else if (godName.equalsIgnoreCase(ATLAS.getName())) {
            return ATLAS;
        } else if (godName.equalsIgnoreCase(DEMETER.getName())) {
            return DEMETER;
        } else if (godName.equalsIgnoreCase(PAN.getName())) {
            return PAN;
        } else if (godName.equalsIgnoreCase(MINOTAUR.getName())) {
            return MINOTAUR;
        } else if (godName.equalsIgnoreCase(PROMETHEUS.getName())) {
            return PROMETHEUS;
        } else if (godName.equalsIgnoreCase(HEPHAESTUS.getName())) {
            return HEPHAESTUS;
        } else if (godName.equalsIgnoreCase(CHARON.getName())) {
            return CHARON;
        } else if (godName.equalsIgnoreCase(HESTIA.getName())) {
            return HESTIA;
        } else if (godName.equalsIgnoreCase(MEDUSA.getName())) {
            return MEDUSA;
        } else if (godName.equalsIgnoreCase(POSEIDON.getName())) {
            return POSEIDON;
        } else if (godName.equalsIgnoreCase(ARES.getName())) {
            return ARES;
        } else if (godName.equalsIgnoreCase(ZEUS.getName())) {
            return ZEUS;
        } else {
            throw new IllegalArgumentException();
        }
    }
}