package it.polimi.ingsw.client;

import it.polimi.ingsw.bean.action.Action;
import it.polimi.ingsw.bean.action.ActionFactory;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.SetupOptions;
import it.polimi.ingsw.client.view.CLI;
import it.polimi.ingsw.client.view.GUI.GUI;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.controller.Operation;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utilities.MessageType;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Observer<String> {

    private View clientView;
    private static final long TIME_TO_ANSWER = 120000; //milliseconds

    private SocketClientConnection socketClientConnection;
    private Options currentOption;
    private String nickname;

    /**
     * Flag used to check, when inputs from view arrive, if it's the turn of the player linked to this specific view
     */
    private boolean isMyTurn = true;

    private Timer timer;

    /**
     * Since we use a different thread to receive elements from socket and from view,
     * it may happen that controller is notified of an operation when it shouldn't
     */
    private boolean waitingInput = false;


    public String getNickname() {
        return nickname;
    }

    public View getClientView() {
        return clientView;
    }

    /**
     * This method is used to check if the player's input isValid before of sending it to the server.
     * It also handles message received from user when he should wait either while other players are performing
     * their turn or while server is parsing his choices.
     *
     * @param message The message received from CLI or GUI
     */
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
            Object m = MessageParser.parseMessage(currentOption, message);
            Action action;
            if (nickname == null) {
                action = ActionFactory.createAction(currentOption.getCurrentOperation(), m, message); //used when the user has to choose a nickname
            } else {
                action = ActionFactory.createAction(currentOption.getCurrentOperation(), m, nickname);
            }
            socketClientConnection.asyncWriteToSocket(action);
            waitingInput = false;
            notifyAll();
        } else {
            clientView.showMessage(errorString);
            startTimerToAnswer();
        }
    }

    /**
     * This method receives Option notified from socket and set properties necessary during the option handling
     *
     * @param options Option notified from the thread which receives the Object from the socket.
     * @throws InterruptedException Exception thrown by wait() method.
     */
    public synchronized void handleOption(Options options) throws InterruptedException {
        while (waitingInput) {
            wait();
        }
        currentOption = options;
        if (options.getMessage().equals(MessageType.NICKNAME_APPROVED)) {
            this.nickname = options.getNickname();
            clientView.setNickname(options.getNickname());
        }
        if (options instanceof SetupOptions) {
            isMyTurn = true;
        } else {
            isMyTurn = currentOption.getNickname().equals(this.nickname);
        }
        clientView.setCurrentOption(options);
        options.execute(clientView);
        if (isMyTurn && currentOption.getCurrentOperation() != Operation.MESSAGE_NO_REPLY) {
            waitingInput = true;
            startTimerToAnswer();
        }
    }

    /**
     * Controller Setup method which calls constructor of either of CLI and GUI in line with players' choice and, in addition,
     * tries to open a new connection with the server using the SocketClientConnection instance.
     */
    public void setup(boolean useGUI) {
        if (useGUI) {
            clientView = new GUI(this);
        } else {
            clientView = new CLI();
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

    /**
     * This method provide a scheduled Timer that notify to the players both when the provided input time is about to finish
     * and when the aforementioned time is up and the player looses the game.
     */
    private void startTimerToAnswer() {
        timer = new Timer();
        timer.schedule(checkTime(), TIME_TO_ANSWER);
        timer.schedule(printRemainingTime(), TIME_TO_ANSWER / 2);
    }

    /**
     * This method provides a new timerTask which checks, after a defined amount of milliseconds, if the due time,
     * in which the player has to send his answer, is up;
     */
    private TimerTask checkTime() {
        return new TimerTask() {
            @Override
            public void run() {
                if (waitingInput) {
                    clientView.showMessage("\nTime to answer is up, you loose");
                    clientView.close();
                    socketClientConnection.closeConnection();
                }
            }
        };
    }

    /**
     * This method provides a new timerTask which notify to the player tha amount of remaining time in which he has to send
     * his answer
     */
    private TimerTask printRemainingTime() {
        return new TimerTask() {
            @Override
            public void run() {
                clientView.showMessage("\nHurry Up, only " + TIME_TO_ANSWER / 2000 + " seconds remain to answer.");
            }
        };
    }


    void reset() {
        timer.cancel();
        clientView.close();
    }

    public Options getCurrentOption() {
        return currentOption;
    }

    public boolean isMyTurn() {
        return isMyTurn;
    }
}
