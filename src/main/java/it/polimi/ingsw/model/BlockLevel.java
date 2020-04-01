package it.polimi.ingsw.model;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum BlockLevel {
    GROUND("Ground"), ONE("First level"), TWO("Second level"), THREE("Third level");

    private final String message;

    BlockLevel(String message) {
        this.message = message;
    }

    public BlockLevel nextLevel() {
        switch (this) {
            case GROUND:
                return ONE;
            case ONE:
                return TWO;
            case TWO:
            case THREE:
                return THREE;
            default:
                return GROUND;
        }
    }

    @Override
    public String toString() {
        return message;
    }
}