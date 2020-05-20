package it.polimi.ingsw.model;

/**
 * BlockLevel represent the lever of a tower, if the level is GROUND the tower has not been built
 */
public enum BlockLevel {
    GROUND("Ground", 0), ONE("First level", 1), TWO("Second level", 2), THREE("Third level", 3);

    private final String message;
    private final int levelInt;

    BlockLevel(String message, int i) {
        this.message = message;
        this.levelInt = i;
    }

    public int getLevelInt() {
        return levelInt;
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
                throw new IllegalArgumentException();
        }
    }

    public BlockLevel previousLevel() {
        switch (this) {
            case THREE:
                return TWO;
            case TWO:
                return ONE;
            case ONE:
            case GROUND:
                return GROUND;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return message;
    }
}