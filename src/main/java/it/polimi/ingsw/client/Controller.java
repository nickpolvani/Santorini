package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionFactory;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.MessageType;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Scanner;

public class Controller implements Observer<String> {

    private View clientView;
    private SocketClientConnection socketClientConnection;
    private Options currentOption;
    private String nickname;

    private boolean isMyTurn = true;
    private final String alredyProcessedOptionsMessage = "Hai già risposto";
    private boolean waitingInput;

    public String getNickname() {
        return nickname;
    }

    @Override
    public synchronized void update(String message) {
        if (!waitingInput) {
            clientView.showMessage(alredyProcessedOptionsMessage);
            return;
        }
        String errorString = currentOption.isValid(message);
        if (!isMyTurn) {
            clientView.showMessage("It's " + currentOption.getNickname() + "'s turn. Wait until it's your turn");
            return;
        }
        if (errorString == null) {
            Object m = Message.parseMessage(currentOption, message);
            if (m == null) throw new IllegalStateException();
            Action action;
            if (nickname == null) {
                action = ActionFactory.createAction(currentOption, m, message); //used when the user has to choose a nickname
            } else {
                action = ActionFactory.createAction(currentOption, m, nickname);
            }
            socketClientConnection.asyncWriteToSocket(action);
            waitingInput = false;
            notifyAll();
        } else {
            clientView.showMessage(errorString);
        }
    }

    public synchronized void handleOption(Options options) throws InterruptedException {
        while (waitingInput) {
            wait();
        }
        currentOption = options;
        if (options.getMessageType().equals(MessageType.NICKNAME_APPROVED)) {
            this.nickname = options.getNickname();
            clientView.setNickname(options.getNickname());
        }
        if (options instanceof SetupOptions) {
            isMyTurn = true;
        } else {
            isMyTurn = currentOption.getNickname().equals(this.nickname);
        }
        options.execute(clientView);
        if (isMyTurn && !(currentOption.getCurrentOperation() == Operation.MESSAGE_NO_REPLY)) {
            waitingInput = true;
        }
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
        } catch (ConnectException e) {
            clientView.showMessage(e.getMessage());
            clientView.showMessage("Connection refused. Cannot ping server");
        } catch (IOException e) {
            clientView.showMessage(e.getMessage());
            clientView.showMessage("Connection closed on Server-side");
        } finally {
            tearDown();
        }
    }

    private void tearDown() {
        clientView.close();
    }
}
