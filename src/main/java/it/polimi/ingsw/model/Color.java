package it.polimi.ingsw.model;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum Color {
    RED("red", 0), BLUE("blue", 1), GREEN("green", 2);

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
                return GREEN;
            default:
                throw new IllegalArgumentException();
        }
    }
}