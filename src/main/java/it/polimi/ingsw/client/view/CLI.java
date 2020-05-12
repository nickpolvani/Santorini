package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.IslandBoard;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class CLI extends View {

    private boolean yourTurn;


    @Override
    public void showMessage(String message) {

        new Thread(() -> {
            System.out.println(message);
        }).start();

    }

    public void waitForResponse() {
        setYourTurn(true);
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();
        setYourTurn(false);
        new Thread(() -> {
            while (!isYourTurn()) {
                if (new Scanner(System.in).hasNext()) {
                    System.out.println("It's not your turn yet. Wait for it!");
                }
            }
        }).start();
    }

    @Override
    public void updateBoard(IslandBoard board) {

    }

    public void printWelcome() {

        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();

        String welcome =
                " _       __     __                             ______         _____             __             _       _ \n" +
                        "| |     / /__  / /________  ____ ___  ___     /_  __/___     / ___/____ _____  / /_____  _____(_)___  (_)\n" +
                        "| | /| / / _ \\/ / ___/ __ \\/ __ `__ \\/ _ \\     / / / __ \\    \\__ \\/ __ `/ __ \\/ __/ __ \\/ ___/ / __ \\/ / \n" +
                        "| |/ |/ /  __/ / /__/ /_/ / / / / / /  __/    / / / /_/ /   ___/ / /_/ / / / / /_/ /_/ / /  / / / / / /  \n" +
                        "|__/|__/\\___/_/\\___/\\____/_/ /_/ /_/\\___/    /_/  \\____/   /____/\\__,_/_/ /_/\\__/\\____/_/  /_/_/ /_/_/   \n";

        AnsiConsole.out().println(ansi().eraseLine().fg(Ansi.Color.BLUE).a(welcome).reset());

    }

    public void printBoard(IslandBoard board) {

        StringBuilder utility = new StringBuilder();

        utility.append(
                ">>  L# : It means that on the tile there is a block of level n° '#'\n" +
                        ">>  W : There is a worker on tile. Every worker has the color of his team!\n" +
                        ">>  / \\" + "\n" +
                        "    \\ / : A dome has been built on thi tile\n\n"
        );


        char[] rep = board.toString().toCharArray();

        for (int i = 0; i < rep.length; i++) {
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
                i = i + 2;
            } else if (rep[i] == '/') {
                utility.append("@|magenta / \\|@");
                i = i + 2;
            } else if (rep[i] == '\\') {
                utility.append("@|magenta \\ /|@");
                i = i + 2;
            } else {
                utility.append(rep[i]);
            }
        }
        System.setProperty("jansi.passthrough", "true");
        AnsiConsole.systemInstall();
        AnsiConsole.out().println(ansi().eraseLine().render(utility.toString()));
    }


    public synchronized boolean isYourTurn() {
        return yourTurn;
    }

    public synchronized void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }
}
