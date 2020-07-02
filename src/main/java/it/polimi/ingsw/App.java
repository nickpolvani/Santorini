package it.polimi.ingsw;

import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.server.Server;
import org.apache.log4j.xml.DOMConfigurator;

import java.nio.file.FileSystems;

public class App {

    public static void main(String[] args) {

        if (args.length != 1) {
            printError("You have to insert only one parameter");
        }
        switch (args[0].toLowerCase()) {
            case "server":
                runServer();
                break;
            case "gui":
                runGui();
                break;
            case "cli":
                runCli();
                break;
            default:
                printError("The parameter inserted is not valid");
        }
    }

    private static void printError(String message) {
        System.out.println(message);
        System.exit(-1);
    }

    private static void runServer() {
        DOMConfigurator.configure(FileSystems.getDefault().getPath("").toAbsolutePath() + "\\src\\main\\resources\\myLogConfig.xml");
        Server server = Server.getInstance();
        if (server != null) server.run();
    }

    private static void runCli() {
        Controller controller = new Controller();
        controller.setup(false);
    }

    private static void runGui() {
        Controller controller = new Controller();
        controller.setup(true);
    }
}
