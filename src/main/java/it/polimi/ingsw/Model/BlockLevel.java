package it.polimi.ingsw.Model;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum BlockLevel {
    GROUND, ONE, TWO, THREE;

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
}