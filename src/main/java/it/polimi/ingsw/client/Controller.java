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
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Observer<String> {

    private View clientView;
    private SocketClientConnection socketClientConnection;
    private Options currentOption;
    private String nickname;

    private boolean isMyTurn = true;
    private boolean waitingInput;

    private final long timeToAnswer = 30000; //milliseconds
    private Timer timer;


    public String getNickname() {
        return nickname;
    }

    public View getClientView() {
        return clientView;
    }

    @Override
    public synchronized void update(String message) {
        if (!waitingInput) {
            if (!isMyTurn) {
                clientView.showMessage("It's " + currentOption.getNickname() + "'s turn. Wait until it's your turn");
                return;
            }
            clientView.showMessage("You have already answered, wait...");
            return;
        }
        timer.cancel();
        String errorString = currentOption.isValid(message);
        if (errorString == null) {
            Object m = Message.parseMessage(currentOption, message);
            if (m == null) {
                throw new IllegalStateException();
            }
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
            startTimerToAnswer();
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
            startTimerToAnswer();
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
            clientView.close();
        }
    }


    private void startTimerToAnswer() {
        timer = new Timer();
        timer.schedule(checkTime(), timeToAnswer);
        timer.schedule(printRemainingTime(), timeToAnswer / 2);
    }

    private TimerTask checkTime() {
        return new TimerTask() {
            @Override
            public void run() {
                if (waitingInput) {
                    clientView.showMessage("Time to answer is up, you loose");
                    try {
                        clientView.close();
                        socketClientConnection.closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private TimerTask printRemainingTime() {
        return new TimerTask() {
            @Override
            public void run() {
                clientView.showMessage("Hurry Up, only " + timeToAnswer / 2000 + " seconds remain to answer");
            }
        };
    }
}
