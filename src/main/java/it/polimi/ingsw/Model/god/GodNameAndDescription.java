package it.polimi.ingsw.model.god;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum GodNameAndDescription {
    APOLLO("Apollo", ""),
    ARTEMIS("Artemis", ""),
    ATHENA("Athena", ""),
    ATLAS("Atlas", ""),
    DEMETER("Demeter", ""),
    PAN("Pan", ""),
    MINOTAUR("Minotaur", ""),
    PROMETHEUS("Prometheus", ""),
    HEPHAESTUS("Hephaestus", "");

    private final String name;
    private final String descriptionOfPower;

    GodNameAndDescription(String name, String description) {
        this.name = name;
        this.descriptionOfPower = description;
    }

    String getName() {
        return name;
    }

    String getDescriptionOfPower() {
        return descriptionOfPower;
    }
}