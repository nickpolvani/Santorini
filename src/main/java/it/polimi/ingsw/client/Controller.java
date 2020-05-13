package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionFactory;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.observer.Observer;

import java.io.IOException;
import java.util.Scanner;

public class Controller implements Observer<String> {

    private View clientView;
    private SocketClientConnection socketClientConnection;
    private Options currentOption;
    private String nickname;

    private boolean isMyTurn = true;

    @Override
    public void update(String message) {
        String errorString = currentOption.isValid(message);
        if (!isMyTurn) {
            clientView.showMessage("It's " + currentOption.getNickname() + "'s turn. Wait until it's your turn");
            return;
        }
        if (errorString == null) {
            Object m = Message.parseMessage(currentOption, message);
            Action action;
            if (nickname == null) {
                action = ActionFactory.createAction(currentOption, m, message); //used when the user has to choose a nickname
            } else {
                action = ActionFactory.createAction(currentOption, m, nickname);
            }
            socketClientConnection.asyncWriteToSocket(action);
        } else {
            clientView.showMessage(errorString);
        }


    }

    public void handleOption(Options options) {
        if (options.getMessageType() == Options.MessageType.NICKNAME_APPROVED) {
            this.nickname = ((SetupOptions) options).getNickname();
            clientView.setNickname(((SetupOptions) options).getNickname());
        }
        if (options instanceof SetupOptions) {
            isMyTurn = true;
        } else {
            isMyTurn = currentOption.getNickname().equals(this.nickname);
        }
        currentOption = options;
        options.execute(clientView);
    }

    public void setup() {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (clientView == null) {
            System.out.println("Choose whether you want to play with GUI or CLI. Enter [CLI/GUI]");
            input = scanner.nextLine();
            if (input.toUpperCase().equals("CLI")) {
                clientView = new CLI();
            } else if (input.toUpperCase().equals("GUI")) {
                clientView = new GUI();
            }
        }
        clientView.addObserver(this);
        try {
            clientView.start();
            this.socketClientConnection = new SocketClientConnection(this);
            this.socketClientConnection.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
