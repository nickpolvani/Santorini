package it.polimi.ingsw;

import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.server.Server;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.IOException;
import java.nio.file.FileSystems;

public class App {

    public static void main(String[] args) {

        if (args[0].equalsIgnoreCase("server")) {
            Server server;
            Logger logger = Logger.getLogger("Server");

            DOMConfigurator.configure(FileSystems.getDefault().getPath("").toAbsolutePath() + "\\src\\main\\resources\\myLogConfig.xml");
            try {
                server = new Server();
                server.run();
            } catch (IOException e) {
                logger.fatal("The server cannot be initialized");
            }
        } else {
            Controller controller = new Controller();
            controller.setup(args[0].equalsIgnoreCase("gui"));
        }
    }
}
