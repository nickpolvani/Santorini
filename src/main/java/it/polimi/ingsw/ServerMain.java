package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
        Server server;
        Logger logger = Logger.getLogger("Server");
        DOMConfigurator.configure("src/main/resources/myLogConfig.xml");
        try {
            server = new Server();
            server.run();
        } catch (IOException e) {
            logger.fatal("The server cannot be initialized");
        }
    }
}
