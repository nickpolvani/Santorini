package it.polimi.ingsw.model;

/**
 * @author Polvani-Puoti-Sacchetta
 */
public enum Color {
    RED("red"), BLUE("blue"), GREEN("green");

    private String message;

    Color(String message) {
        this.message = message;
    }
}