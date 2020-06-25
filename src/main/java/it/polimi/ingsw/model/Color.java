package it.polimi.ingsw.model;

/**
 * color of the players' workers
 */
public enum Color {
    RED("RED", 0), BLUE("BLUE", 1), YELLOW("YELLOW", 2);

    private final String message;
    private final int num;

    Color(String message, int num) {
        this.message = message;
        this.num = num;
    }

    public String getMessage() {
        return message;
    }

    public static Color parseColor(int num) {
        switch (num) {
            case 0:
                return RED;
            case 1:
                return BLUE;
            case 2:
                return YELLOW;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int getNum() {
        return num;
    }
}