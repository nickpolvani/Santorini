package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.PlaceWorkersOptions;
import it.polimi.ingsw.client.Controller;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.utilities.MessageType;

import javax.swing.*;
import java.awt.*;


public class GUI extends View {

    JFrame frame = new JFrame("Santorini");
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private ActivePanel activePanel;
    private final Controller controller;
    private Options currentOptions;
    private boolean gameStarted;


    public GUI(Controller controller) {
        this.board = null;
        this.controller = controller;
    }

    public Dimension getScreenSize() {
        return screenSize;
    }

    @Override
    public void showMessage(String message) {
        this.currentOptions = controller.getCurrentOption();

        if (!gameStarted) {
            if (message.equals(MessageType.CHOOSE_LOBBY_SIZE)) {
                setActivePanel(new SelectLobbySizePanel(this));
            } else if (currentOptions instanceof GodOptions && currentOptions.getNickname().equals(controller.getNickname())) {
                setActivePanel(new ChooseGodPanel(this, (GodOptions) currentOptions));
            } else if (currentOptions instanceof PlaceWorkersOptions && currentOptions.getNickname().equals(controller.getNickname())) {
                gameStarted = true;
                setActivePanel(new GamePanel(this, currentOptions));
            }
        }
        activePanel.setCurrentOptions(currentOptions);
        activePanel.showMessage(message);
    }

    @Override
    public void updateBoard(IslandBoard board) {

    }

    @Override
    public void readInput() {

    }


    @Override
    public void start() {

        ActivePanel activePanel = new SetupPanel(this);
        this.activePanel = activePanel;
        frame.setSize(screenSize);
        frame.setResizable(false);
        frame.add(activePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void close() {

    }

    private void setActivePanel(ActivePanel activePanel) {
        frame.remove(this.activePanel);
        this.activePanel = activePanel;
        frame.add(activePanel);
    }

    public Options getCurrentOptions() {
        return currentOptions;
    }


}
