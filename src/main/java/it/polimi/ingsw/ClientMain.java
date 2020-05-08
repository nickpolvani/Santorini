package it.polimi.ingsw;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        View view;
        System.out.println("Con cosa vuoi giocare?");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        switch (input.toUpperCase()) {
            case "CLI":
                view = new CLI();
                break;
            case "GUI":
                view = new GUI();
                break;
            default:
                throw new IllegalArgumentException();
        }

        Client client = new Client("127.0.0.1", 12345, new Controller(view));
        try {
            client.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
