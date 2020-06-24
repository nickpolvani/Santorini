package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.IslandBoard;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLI extends View {

    private boolean active = true;
    private final Scanner in = new Scanner(System.in);

    public CLI() {
        printWelcome();
    }

    @Override
    public void showMessage(String message) {
        new Thread(() ->
                System.out.println(message)
        ).start();
    }

    public void readInput() {
        while (isActive()) {
            String input = in.nextLine();
            if (isActive()) {
                notify(input);
            }
        }
    }

    public void start() {
        new Thread(this::readInput).start();
    }

    public boolean isActive() {
        return active;
    }

    public void printWelcome() {


        // This Line is necessary only if you want to open client in Terminal of Intellij. Either powershell or cmd does not need this

        System.setProperty("jansi.passthrough", "true");

        AnsiConsole.systemInstall();

        String welcome =
                " _       __     __                             ______         _____             __             _       _ \n" +
                        "| |     / /__  / /________  ____ ___  ___     /_  __/___     / ___/____ _____  / /_____  _____(_)___  (_)\n" +
                        "| | /| / / _ \\/ / ___/ __ \\/ __ `__ \\/ _ \\     / / / __ \\    \\__ \\/ __ `/ __ \\/ __/ __ \\/ ___/ / __ \\/ / \n" +
                        "| |/ |/ /  __/ / /__/ /_/ / / / / / /  __/    / / / /_/ /   ___/ / /_/ / / / / /_/ /_/ / /  / / / / / /  \n" +
                        "|__/|__/\\___/_/\\___/\\____/_/ /_/ /_/\\___/    /_/  \\____/   /____/\\__,_/_/ /_/\\__/\\____/_/  /_/_/ /_/_/   \n";

        AnsiConsole.out().println(ansi().eraseLine().fg(Ansi.Color.RED).a(welcome).reset());

    }

    public void updateBoard(IslandBoard board) {
        this.board = board;
        StringBuilder utility = new StringBuilder();

        utility.append(
                "@|yellow >>  L# : It means that on the tile there is a block of level n° '#'\n" +
                        ">>  W : There is a worker on tile. Every worker has the color of his team!\n" +
                        ">>  / \\" + "\n" +
                        "    \\ / : A dome has been built on tile|@ \n"
        );


        char[] rep = board.toString().toCharArray();
        int i = 0;
        while (i < rep.length) {
            if (rep[i] == 'w') {
                switch (rep[i + 2]) {
                    case '0':
                        utility.append(" ").append("@|red W|@").append(" ");
                        break;
                    case '1':
                        utility.append(" ").append("@|blue W|@").append(" ");
                        break;
                    case '2':
                        utility.append(" ").append("@|green W|@").append(" ");
                        break;
                    default:
                        throw new IllegalArgumentException("Not Valid Worker Color");
                }
                i += 3;
            } else if (rep[i] == '/') {
                utility.append("@|magenta / \\|@");
                i += 3;
            } else if (rep[i] == '\\') {
                utility.append("@|magenta \\ /|@");
                i += 3;
            } else {
                utility.append(rep[i]);
                i++;
            }
        }


        //This Line is necessary only if you want to open client in Terminal of Intellij. Either powershell or cmd does not need this
        System.setProperty("jansi.passthrough", "true");

        AnsiConsole.systemInstall();
        AnsiConsole.out().println(ansi().eraseLine().render(utility.toString()));
    }

    public void close() {
        active = false;
    }
}

