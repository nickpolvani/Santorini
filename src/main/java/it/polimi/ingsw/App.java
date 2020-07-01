package it.polimi.ingsw;

import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.server.Server;
import org.apache.log4j.xml.DOMConfigurator;

import java.nio.file.FileSystems;

public class App {

    public static void main(String[] args) {

        if (args[0].equalsIgnoreCase("server")) {
            DOMConfigurator.configure(FileSystems.getDefault().getPath("").toAbsolutePath() + "\\src\\main\\resources\\myLogConfig.xml");
            Server server = Server.getInstance();
            if (server != null) server.run();
        } else {
            Controller controller = new Controller();
            controller.setup(args[0].equalsIgnoreCase("gui"));
        }
    }
}
