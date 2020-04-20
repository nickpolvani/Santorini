package it.polimi.ingsw.model.god;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum GodDescription {
    APOLLO("Apollo", "Your Worker may  move into an opponent Worker’s space by forcing their Worker to the space just vacated."),
    ARTEMIS("Artemis", "Your Worker may move one additional time, but not back to its initial space."),
    ATHENA("Athena", "If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn."),
    ATLAS("Atlas", "Your Worker may build a dome at any level."),
    DEMETER("Demeter", "Your Worker may build one additional time, but not on the same space."),
    PAN("Pan", "You also win if your worker moves down two or more levels"),
    MINOTAUR("Minotaur", "Your Worker may  move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level."),
    PROMETHEUS("Prometheus", "If your Worker does not move up, it may build both before and after moving."),
    HEPHAESTUS("Hephaestus", "Your Worker may build one additional block (not dome) on top of your first block.");

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
}